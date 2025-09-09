# Systemd-Deploy-backend.ps1 (자동배포)

# 변수 설정
$RemoteHost = "13.209.18.102"
$RemotePort = "22"
$RemoteUser = "binblurdev"
$RemoteDir = "/home/$RemoteUser"
$ServiceName = "binblur-backend.service"

# 현재 경로 사용
$CurrentPath = Get-Location
$JarPath = Join-Path $CurrentPath "binblur-api\build\libs\binblur-api.jar"
$JarName = "binblur-api.jar"

# 로그 함수
function Log-Message {
    param([string]$message)
    Write-Host "$(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') - $message"
}

# 오류 처리 함수
function Handle-Error {
    param([string]$message)
    Log-Message "Error: $message"
    exit 1
}

# JAR 파일 존재 확인
if (-not (Test-Path $JarPath)) {
    Handle-Error "JAR file not found at path: $JarPath"
}

Log-Message "Using JAR file: $JarPath"

# JAR 파일을 원격 서버로 전송
Log-Message "Transferring JAR file to remote server..."
$scpCommand = "scp -P $RemotePort `"$JarPath`" ${RemoteUser}@${RemoteHost}:${RemoteDir}/$JarName"
Log-Message "Executing command: $scpCommand"
Invoke-Expression $scpCommand
if ($LASTEXITCODE -ne 0) {
    Handle-Error "Failed to transfer JAR file to remote server."
}

# 원격 서버에서 서비스 재시작
Log-Message "Restarting service on remote server..."
$sshCommand = "ssh -p $RemotePort ${RemoteUser}@${RemoteHost} `"sudo systemctl restart $ServiceName && echo 'Service restarted successfully.'`""
Log-Message "Executing command: $sshCommand"
$result = Invoke-Expression $sshCommand
if ($LASTEXITCODE -ne 0) {
    Handle-Error "Failed to restart service on remote server."
}

Log-Message $result
Log-Message "Deployment completed successfully!"

# 실시간 로그 확인
Log-Message "Starting real-time log viewing. Press Ctrl+C to stop."
$logCommand = "ssh -p $RemotePort ${RemoteUser}@${RemoteHost} `"sudo journalctl -u $ServiceName -f`""
Log-Message "Executing command: $logCommand"
try {
    Invoke-Expression $logCommand
}
catch {
    Log-Message "Log viewing stopped."
}
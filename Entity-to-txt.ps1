# 실행방법 커맨드에 아래입력
# .\Entity-to-txt.ps1

# 엔티티 디렉토리 설정
$ENTITY_DIR = "C:\work\binblur-backend\binblur-core\src\main\java\com\binblur\core\entity"

# 결과 파일 설정
$OUTPUT_FILE = "Binblur-Entity.txt"

# 파일 초기화
Clear-Content -Path $OUTPUT_FILE -ErrorAction SilentlyContinue

# 각 Java 파일에 대해 처리
Get-ChildItem -Path $ENTITY_DIR -Filter *.java | ForEach-Object {
    $fileName = $_.Name
    $filePath = $_.FullName

    Add-Content -Path $OUTPUT_FILE -Value "Entity: $($fileName -replace '\.java$', '')"
    Add-Content -Path $OUTPUT_FILE -Value "----------------------------------------"

    # 파일 내용 읽기 (UTF-8 인코딩 사용)
    $content = Get-Content -Path $filePath -Raw -Encoding UTF8

    # @Schema 어노테이션에서 설명 추출
    $schemaMatch = [regex]::Match($content, '@Schema\(.*?description\s*=\s*"([^"]+)"')
    if ($schemaMatch.Success) {
        $description = $schemaMatch.Groups[1].Value
        Add-Content -Path $OUTPUT_FILE -Value "Description: $description"
    }

    Add-Content -Path $OUTPUT_FILE -Value ""

    # 필드 정보 추출
    $fieldMatches = [regex]::Matches($content, '(?ms)(/\*\*.*?\*/\s*)?private\s+(\w+(?:<[^>]+>)?)\s+(\w+)\s*;')
    foreach ($match in $fieldMatches) {
        $comment = $match.Groups[1].Value.Trim()
        $fieldType = $match.Groups[2].Value
        $fieldName = $match.Groups[3].Value

        # 주석에서 설명 추출
        $fieldDescription = "No description"
        if ($comment -match '\*\s*(.+)') {
            $fieldDescription = $Matches[1].Trim()
        }

        Add-Content -Path $OUTPUT_FILE -Value "Field: $fieldName"
        Add-Content -Path $OUTPUT_FILE -Value "  Type: $fieldType"
        Add-Content -Path $OUTPUT_FILE -Value "  Description: $fieldDescription"
    }

    Add-Content -Path $OUTPUT_FILE -Value ""
}

Write-Host "Detailed entity structure has been extracted to $OUTPUT_FILE"
# aws서버
# 15.164.66.87 프론트
# 13.209.18.102 백엔드
# id:shopdev
# pw:shop1

# 프로젝트 배포 방법

### 1. 부트자르 빌드 => 인텔리제이 gradle 탭에서 실행

1.clean
2.bootjar

프로젝트 최상단 위치
shop-backend\shop-api\build\libs

shop-api.jar 파일생성

서버에 배포시 /dev 에 jar 파일을 복사한다.
서버에 

### 2. 서버 배포 스크립트실행 (서버업로드, bootjar재실작 , 로그확인)

파일실행 원격배포 스크립트(암호필요)

```sh
.\Deploy-backend.ps1
```

### etc (서버내부사용)

systemd에 새 서비스 파일을 인식

sudo systemctl daemon-reload

서비스를 활성화
sudo systemctl enable shop-backend.service
서비스를 시작
sudo systemctl start shop-backend.service
서비스 중지
sudo systemctl stop shop-backend.service
서비스 재시작
sudo systemctl restart shop-backend.service
로그 확인
sudo journalctl -u shop-backend.service
실시간 로그
tail -f /home/shopdev/logs/api-$(date +%Y-%m-%d).log
서비스 상태를 확인
sudo systemctl status shop-backend.service
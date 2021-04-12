# TmapTest
 for toy project #1 group navigation
 Tmap API test
## 기능 테스트 완료

>2021.03.14
>
>1. 마커 추가

    TMapMarkerItem st_marker = new TMapMarkerItem();
    TMapPoint st_point = new TMapPoint(37.73213660662052, 127.08287877338195);
    // Marker Icon
    Bitmap st_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_b_m_s);
    st_marker.setIcon(st_icon);
    st_marker.setPosition(0.5f, 1.0f);
    st_marker.setTMapPoint(st_point);
    st_marker.setName("SKT타워");
    tMapView.addMarkerItem("st_marker", st_marker);

>2. 지도 클릭 이벤트

    tMapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {...});
    tMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {...});

>3. 지도 스크롤 이벤트

    // 지도 스크롤 종료
    tMapView.setOnDisableScrollWithZoomLevelListener(new TMapView.OnDisableScrollWithZoomLevelCallback() {...});
    // 지도 스크롤 시작
    tMapView.setOnEnableScrollWithZoomLevelListener(new TMapView.OnEnableScrollWithZoomLevelCallback() {...});

>4. 마커 클릭 이벤트

    // MarkeyOverlay.java
    // MarkerItem2 아이콘 클릭 시 이벤트 발생
    public boolean onSingleTapUp(PointF point, TMapView mapView) {...}

>5. 풍선뷰 클릭 이벤트

    // MarkerItem2 풍선뷰 클릭 시 이벤트 발생
    tMapView.setOnMarkerClickEvent(new TMapView.OnCalloutMarker2ClickCallback() {...});

>6. 경로 탐색

    // Find path of car or pedestrian and return TMapData
    TMapData tmapdata = new TMapData();
    tmapdata.findPathDataAllType(TMapData.TMapPathType.CAR_PATH, st_point, ed_point, new TMapData.FindPathDataAllListenerCallback() {...});

>7. 경로 추출

    public void onFindPathDataAll(Document document) {...}

>8. 지도에 경로 표시

    TMapPolyLine tMapPolyLine = new TMapPolyLine();
    tMapPolyLine.setLineColor(Color.RED);
    tMapPolyLine.setLineWidth(20);
    tMapPolyLine.setOutLineAlpha(0);
    for( int i=0; i<coordsList.size(); i++ ) {
     tMapPolyLine.addLinePoint( coordsList.get(i) );
    }
    tMapView.addTMapPolyLine("Line", tMapPolyLine);

>9. 보행자 경로 탐색

    // Find path of car or pedestrian and return TMapData
    TMapData tmapdata = new TMapData();
    tmapdata.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, st_point, ed_point, new TMapData.FindPathDataAllListenerCallback() {...});

>10. POI 검색

    // find POI
    tMapData.findAllPOI(strData,30, new TMapData.FindAllPOIListenerCallback() {...});

## 추후 진행 사항

>1. POI 검색 화면 뷰 구성
>2. 내비게이션 뷰 구성
>3. 다른 그룹 멤버 내비게이션 뷰 구성
>4. 팝업 이벤트 설정(목적지 및 경유지 공유, 메시지 수신 등)
>5. 경유지 포함한 경로 탐색

//声明变量map、layer、url
var map, layer, url = "http://localhost:8090/iserver/services/map-baidu/rest/maps/normal";
var url2 = "http://localhost:8090/iserver/services/data-editMap/rest/data";
//var layerUrl = "http://localhost:8090/iserver/services/map-editMap/rest/maps/main@ORCL";
var layerUrl = "http://localhost:8090/iserver/services/map-editMap/rest/maps/Test@ORCL";
var url3 = "http://localhost:8090/iserver/services/data-editMap/rest/data/datasources/ORCL/datasets/Test";
var url4 = "http://localhost:8090/iserver/services/data-editMap/rest/data/datasources/ORCL/datasets/Line";
var layer2, vectorLayer, div, markerlayer, marker, drawPoint, drawLine, drawPolygon, pointLayer, lineLayer, polygonLayer;
var modifyFeature, editLayer,ids,userids,loginUserId,isAdmin;
//创建地图控件
function init() {
	//初始化地图
	// map = new SuperMap.Map ("map");
	//新建点矢量图层
	//pointLayer = new SuperMap.Layer.Vector("pointLayer");
	//新建线矢量图层
	//lineLayer = new SuperMap.Layer.Vector("lineLayer");
	//新建面矢量图层
	//polygonLayer = new SuperMap.Layer.Vector("polygonLayer");
	/*drawPoint = new SuperMap.Control.DrawFeature(pointLayer,
			SuperMap.Handler.Point, {
				multi : true
			});
	
	drawLine = new SuperMap.Control.DrawFeature(lineLayer,
			SuperMap.Handler.Path, {
				multi : true
			});*/
	//drawPolygon = new SuperMap.Control.DrawFeature(polygonLayer,SuperMap.Handler.Polygon);
	//
	vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
	//面
	drawPolygon = new SuperMap.Control.DrawFeature(vectorLayer,SuperMap.Handler.Polygon);
	drawPolygon.events.on({"featureadded" : addFeatureCompleted});
	//点
	drawPoint = new SuperMap.Control.DrawFeature(vectorLayer,SuperMap.Handler.Point);
	drawPoint.events.on({"featureadded" : selectedFeatureCompleted});
	//线
	drawLine = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Path);
	drawLine.events.on({"featureadded": addLineFeatureCompleted});
	//markerlayer = new SuperMap.Layer.Markers("markerLayer");
	map = new SuperMap.Map("map", {
		controls : [
		//new SuperMap.Control.LayerSwitcher(),
		//地图平移缩放控件
		// new SuperMap.Control.PanZoomBar(),
		new SuperMap.Control.Navigation({
			dragPanOptions : {
				enableKinetic : true
			}
		}), new SuperMap.Control.Zoom(), drawPoint, drawLine, drawPolygon ],
		allOverlays : true
	});
	//添加经纬度控件
	map.addControl(new SuperMap.Control.MousePosition());
	//添加地图图层切换控件
	map.addControl(new SuperMap.Control.LayerSwitcher());
	//添加比例尺控件
	map.addControl(new SuperMap.Control.ScaleLine(),
			new SuperMap.Pixel(10, 570));
	
	
	map.events.on({
		//"click" : callbackFunction
	});
	//创建分块动态REST图层，该图层显示iserver 8C 服务发布的地图,
	//初始化图层，其中"world"为图层名称，url图层的服务地址，{transparent: true}设置到url的可选参数
	layer = new SuperMap.Layer.TiledDynamicRESTLayer("成都map", url, null, {
		maxResolution : "auto"
	});
//	layer2 = new SuperMap.Layer.TiledDynamicRESTLayer("text", url2, null, {
//		maxResolution : "auto"
//	});
	editLayer = new SuperMap.Layer.TiledDynamicRESTLayer("覆盖物", layerUrl, {
		transparent : true,
		cacheEnabled : false
	}, {
		maxResolution : "auto",
		bufferImgCount : 0
	});

	markerlayer = new SuperMap.Layer.Markers("点");
	//添加图层命名为“Vector Layer”
	//vectorLayer = new SuperMap.Layer.Vector("Vector Layer");
	//
	vectorLayer.events.on({
		"afterfeaturemodified" : editFeatureCompleted
	});
	modifyFeature = new SuperMap.Control.ModifyFeature(vectorLayer);
	map.addControl(modifyFeature);
	//modifyFeature = new SuperMap.Control.ModifyFeature(vectorLayer);
	//
	/*drawPoint = new SuperMap.Control.DrawFeature(vectorLayer,
			SuperMap.Handler.Point);
	drawPoint.events.on({
		"featureadded" : selectedFeatureCompleted
	});*/
	
	//监听图层信息加载完成事件
	layer.events.on({
		"layerInitialized" : addLayer
	});
	//使用register()接口监听事件
	//layer.events.register("layerInitialized", undefined, addLayer);
	//使用un()接口监听事件
	//layer.events.un({"layerInitialized": addLayer});
	//使用unregister()接口监听事件
	//layer.events.unregister("layerInitialized", undefined, addLayer)
	//addData();
	//andPointData();
	
}
//异步加载图层
function addLayer() {
	//将Layer图层加载到Map对象上
	map.addLayer(layer);
	//map.addLayer(layer2);
	//map.addLayer(markerlayer);
	//map.addLayer(editLayer);
	//map.addLayer(pointLayer);
	//map.addLayer(lineLayer);
	//map.addLayer(polygonLayer);
	editLayer.events.on({
		"layerInitialized" : addEditLayer
	});
	//map.addLayers([layer,markerlayer]);
	//map.addLayers(layer);
	//移除图层
	//map.removeLayer(layer);
	//出图，map.setCenter函数显示地图
	//map.setCenter(new SuperMap.LonLat(11584266.08749, 3587388.91251), 0);
	//addFeature();
	//缩放比例 
	//map.zoomTo(11);
}
function addEditLayer() {
	map.addLayers([editLayer,markerlayer]);
	//设置地图中心点，第二个参数为：缩放级别
	map.setCenter(new SuperMap.LonLat(11584266.08749, 3587388.91251), 0);
	//addFeature();
	//缩放比例 
	map.zoomTo(11);
}
//
//function addFeature() {
	//var point = new SuperMap.Geometry.Point(0,0);
	//var pointVector = new SuperMap.Feature.Vector(point);
	//构造geometry对象
	//var point = new SuperMap.Geometry.Point(11584266.08749, 3587388.91251);
	//构建覆盖物对象
	//var pointVector = new SuperMap.Feature.Vector(point);
	//修改点样式红色
	//pointVector.style = {fillColor: "red",strokeColor: "yellow",pointRadius:6};
	//添加矢量图形覆盖物
	//vectorLayer.addFeatures(pointVector);
	//map.addLayer(vectorLayer);
//}
//添加数据
/*function addData(){
	markerlayer.removeMarker(marker);
	var size = new SuperMap.Size(44,33);
	var offset = new SuperMap.Pixel(-(size.w/2), -size.h);
	var icon = new SuperMap.Icon('./project/supermap/theme/images/marker.png', size, offset);
	marker =new SuperMap.Marker(new SuperMap.LonLat(11584266.08749, 3587388.91251),icon) ;
	marker.events.on({
		"click":openInfoWin,
		"touchstart":openInfoWin, //假如要在移动端的浏览器也实现点击弹框，则在注册touch类事件
		"scope": marker
	});
	markerlayer.addMarker(marker);

}
//打开对应的信息框
var infowin = null;
function openInfoWin(){
	closeInfoWin();
	var marker = this;
	var lonlat = marker.getLonLat();
	var size = new SuperMap.Size(0, 33);
	var offset = new SuperMap.Pixel(11, -30);
	var icon = new SuperMap.Icon("../theme/images/marker.png", size, offset);
	var popup = new SuperMap.Popup.FramedCloud("popwin",
		new SuperMap.LonLat(lonlat.lon,lonlat.lat),
		null,"测试鼠标点击事件 ",icon,true);
	infowin = popup;
	map.addPopup(popup);
}
//关闭信息框
 function closeInfoWin(){
	if(infowin){
		try{
			infowin.hide();
			infowin.destroy();
		}
		catch(e){}
	}
}
function draw_point(){
	deactiveAll();
	drawPoint.activate();
}
function draw_line(){
	deactiveAll();
	drawLine.activate();
}
function draw_polygon(){
	deactiveAll();
	drawPolygon.activate();
}
function deactiveAll(){
	drawPoint.deactivate();
	drawLine.deactivate();
	drawPolygon.deactivate();

}
function clearFeatures(){
	deactiveAll();
	pointLayer.removeAllFeatures();
	lineLayer.removeAllFeatures();
	polygonLayer.removeAllFeatures();
} */
//激活添加地物
function activateAddFeature() {
	//先清除上次的显示结果
	vectorLayer.removeAllFeatures();
	clearAllDeactivate();
	drawPolygon.activate();
}
//执行添加地物
function addFeatureCompleted(drawGeometryArgs) {
	drawPolygon.deactivate();
	var geometry = drawGeometryArgs.feature.geometry,
	feature = new SuperMap.Feature.Vector();
	feature.geometry = drawGeometryArgs.feature.geometry,
	feature.style = style;
	vectorLayer.addFeatures(feature);
	geometry.id = "100000";
	var editFeatureParameter,
	editFeatureService,
	features = {
		geometry:geometry
	};
	editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
	features: [features],
	editType: SuperMap.REST.EditType.ADD,
	returnContent:false
	});
	editFeatureService = new SuperMap.REST.EditFeaturesService(url3, {
	eventListeners: {
	"processCompleted": addFeaturesProcessCompleted,
	"processFailed": processFailed
	}
	});
	editFeatureService.processAsync(editFeatureParameter);
}
//添加地物成功
function addFeaturesProcessCompleted(editFeaturesEventArgs) {
	var addResultIds = editFeaturesEventArgs.result.IDs,
	resourceInfo = editFeaturesEventArgs.result.resourceInfo;
	if(addResultIds === null && resourceInfo === null) return;
	
	if((addResultIds && addResultIds.length > 0) || (resourceInfo && resourceInfo.succeed)) {
	alert("新增地物成功");
	vectorLayer.removeAllFeatures();
	//重新加载图层
	//layer.redraw();
	editLayer.redraw();
	}else {
	alert("新增地物失败");
	}
}
//激活选择地物
function activateSelectedFeature() {
	vectorLayer.removeAllFeatures();
	clearAllDeactivate();
	drawPoint.activate();
}
//执行选择地物
function selectedFeatureCompleted(drawGeometryArgs) {
	drawPoint.deactivate(); //11588465.5994987,3588161.34496638
	var getFeaturesByGeometryParams,
	getFeaturesByGeometryService,
	geometry = drawGeometryArgs.feature.geometry;
	getFeaturesByGeometryParams = new SuperMap.REST.GetFeaturesByGeometryParameters({
	datasetNames: ["ORCL:Test"],
	spatialQueryMode: SuperMap.REST.SpatialQueryMode.INTERSECT,
	geometry: geometry
	});
	getFeaturesByGeometryService = new SuperMap.REST.GetFeaturesByGeometryService(url2, {
	eventListeners: {
	"processCompleted": selectedFeatureProcessCompleted,
	"processFailed": processFailed
	}
	});
	getFeaturesByGeometryService.processAsync(getFeaturesByGeometryParams);
}
//选择地物完成
function selectedFeatureProcessCompleted(getFeaturesEventArgs) {
	var features,
	feature,
	i, len,
	originFeatures = getFeaturesEventArgs.originResult.features,
	result = getFeaturesEventArgs.result;
	vectorLayer.removeAllFeatures();
	if(originFeatures === null || originFeatures.length === 0) {
			alert("查询地物为空");
			return;
		}
	ids = new Array();
	userids = new Array();
	//将当前选择的地物的ID保存起来，以备删除地物使用,并在编辑地物中使之为null，以免编辑地物后在不选择地物时将所编辑的地物删除
	for(i = 0, len = originFeatures.length; i < len; i++) {
		ids.push(originFeatures[i].ID);
		userids.push(originFeatures[i].fieldValues[13]);
	}
	if (result && result.features) {
		features = result.features;
		for (var j=0, len = features.length; j<len; j++) {
			feature = features[j];
			feature.style = style;
			vectorLayer.addFeatures(feature);
		}
		//图层绑定人员
		editLayerBindUser(originFeatures[0]);
	}
}
function editLayerBindUser(choosedEditLayer){
	editLayer;
	var editLayerid = choosedEditLayer.ID;
	choosedEditLayer.geometry.style = style;
	
}
//添加标记marklayer
function addMarkLayer(lon, lat, pointId) {
	var size = new SuperMap.Size(44, 33);
	var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
	var icon = new SuperMap.Icon('cluster2.png', size,
			offset);
	var marker = new SuperMap.Marker(new SuperMap.LonLat(lon, lat), icon);
	//将点的id传递给marker
	marker.pointId = pointId;
	markerlayer.addMarker(marker);
	//map.addLayers([ markerlayer ]);
	marker.events.on({
		//"click" : mouseClickHandler,
		//"touchstart" : mouseClickHandler
	//假如要在移动端的浏览器也实现点击弹框，则在注册touch类事件
	});
}


//激活编辑地物
function editselectedFeature() {
	clearAllDeactivate();
	if(ids==null){
	alert("请先选择地物");
	}
	else{
	modifyFeature.activate();
	}

}
//执行地物编辑
function editFeatureCompleted(event) {
	modifyFeature.deactivate();
	var editFeatureParameter,
	editFeatureService,
	features,
	attributes,
	feature = event.feature;
	
	attributes = feature.attributes;
	var attrNames = [];
	var attrValues = [];
	for(var attr in attributes) {
	attrNames.push(attr);
	attrValues.push(attributes[attr]);
	}
	features = {
	fieldNames:attrNames,
	fieldValues:attrValues,
	geometry:event.feature.geometry
	};
	features.geometry.id = feature.fid;
	editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
	features: [features],
	editType: SuperMap.REST.EditType.UPDATE
	});
	editFeatureService = new SuperMap.REST.EditFeaturesService(url3, {
	eventListeners: {
	"processCompleted": updateFeaturesProcessCompleted,
	"processFailed": processFailed
	}
	});
	editFeatureService.processAsync(editFeatureParameter);
}
//更新地物完成
function updateFeaturesProcessCompleted(editFeaturesEventArgs) {
	if(editFeaturesEventArgs.result.resourceInfo.succeed) {
		alert("更新地物成功");
		//重新加载图层
		vectorLayer.removeAllFeatures();
		layer.redraw();
		//使Ids为空，以免编辑地物后在不选择地物时将所编辑的地物删除
		ids=null;
	}else {
		alert("更新地物失败");
	}
}

//删除选中地物
function deleteSelectedFeature() {
	clearAllDeactivate();
	if(ids==null)
	{
	alert("请先选择地物");
	}
	else{
	var editFeatureParameter,
	editFeatureService;
	editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
	IDs: ids,
	editType: SuperMap.REST.EditType.DELETE
	});
	editFeatureService = new SuperMap.REST.EditFeaturesService(url3, {
	eventListeners: {
	"processCompleted": deleteFeaturesProcessCompleted,
	"processFailed": processFailed
	}
	});
	editFeatureService.processAsync(editFeatureParameter);
	}

}
//删除地物完成
function deleteFeaturesProcessCompleted(editFeaturesEventArgs) {
	if(editFeaturesEventArgs.result.resourceInfo.succeed) {
	alert("删除地物成功");
	//重新加载图层
	vectorLayer.removeAllFeatures();
	editLayer.redraw();
	}
	else {
	alert("删除地物失败");
	}
}

//添加铁路线
function addLineFeatureCompleted(editFeaturesEventArgs){
	
	drawLine.deactivate();
	var geometry = editFeaturesEventArgs.feature.geometry,
	feature = new SuperMap.Feature.Vector();
	feature.geometry = editFeaturesEventArgs.feature.geometry,
	feature.style = style;
	vectorLayer.addFeatures(feature);
	
	geometry.id = "100000";
	var editFeatureParameter,
	editFeatureService,
	features = {
	fieldNames:[],
	fieldValues:[],
	geometry:geometry
	};
	editFeatureParameter = new SuperMap.REST.EditFeaturesParameters({
	features: [features],
	editType: SuperMap.REST.EditType.ADD,
	returnContent:false
	});
	editFeatureService = new SuperMap.REST.EditFeaturesService(url4, {
	eventListeners: {
	"processCompleted": processCompleted,
	"processFailed": processFailed
	}
	});
	editFeatureService.processAsync(editFeatureParameter);
}

function processCompleted(editFeaturesEventArgs) {
	
	var addResultIds = editFeaturesEventArgs.result.IDs,
	resourceInfo = editFeaturesEventArgs.result.resourceInfo;
	if(addResultIds === null && resourceInfo === null) return;
	
	if((addResultIds && addResultIds.length > 0) || (resourceInfo && resourceInfo.succeed)) {
	alert("新增线路成功");
	vectorLayer.removeAllFeatures();
	//重新加载图层
	//layer.redraw();
	editLayer.redraw();
	}else {
	alert("新增线路失败");
	}
}
//绘制铁路线
function drawLineFeature() {
	//先清除上次的显示结果
	vectorLayer.removeAllFeatures();
	clearAllDeactivate();
	drawLine.activate();
	}

function clearAllDeactivate() {
	modifyFeature.deactivate();
	drawPoint.deactivate();
	drawPolygon.deactivate();
}
function processFailed(e) {
	alert(e.error.errorMsg);
}
//图层样式定义
/* var Style = new SuperMap.StyleMap(     
			  	 new SuperMap.Style({     
				   	fillColor:"#ffcc33",    
					strokeColor:"#ccff99",
					strokeWidth:2,
					graphZIndex:1})
); */
style = {
	strokeColor : "#304DBE",
	strokeWidth : 1,
	pointerEvents : "visiblePainted",
	fillColor : "#304DBE",
	fillOpacity : 0.8,
	pointRadius : 2
}
//使用图层样式
//Var vectorLayer = new SuperMap.Layer.Vector("Vector Layer", {styleMap: myStyles});
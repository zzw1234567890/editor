window.onload = function(){
	//加载键盘监听
	Key.Init();
	//加载菜单栏配置
	Menu.Init();
	//加载文件栏配置
	Folder.Init();
	//加载提示窗配置
	Hint.Init();
	//加载编辑器配置
	Editor.Init();
	Ws.Init();
	//创建编辑器
	Editor.Create("editor");
	console.log("123");
}
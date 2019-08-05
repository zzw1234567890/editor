function Ws(){
	this.Init = function(){
		Data.IdTest();
		var Ws = new WebSocket("ws://" + domain + "/WebSocket");
		
		Ws.onopen = function(){
			console.log("连接成功");
		}
		Ws.onmessage = function(e){
			var data = JSON.parse(e.data);
			console.log(data);
			switch(data.cmd){
				case "init":
					Data.UserData.id = data.userId;
					var list = data.editorFileEntityList;
					Folder.FileStatusList(list);
				break;
				case "switchFile":
					Folder.FileStatus(data.operator.id,data.newPath,data.oldPath);
				break;
				default:
					console.log("未知命令");
					console.log(data);
				break;
			}
		}
		Ws.onclose = function(){
			console.log("连接关闭");
		}
		setInterval(function(){
			Ws.send("心跳");
		},20000);
	}
}
var Ws = new Ws();
function Folder(){
	this.Init = function(){
		var data = [{
			type:'project',
			name:"project",
			link:"/",
			data:[]
		}];
		this.File(data,$(".editorBox > .bodyerBox > .folderber .folderlist > ul"));
		
		data = Data.FileListData();

		this.File(data,$(".editorBox > .bodyerBox > .folderber .folderlist > ul > li > ul"));

		$(".folderber").contextmenu(function(e){
			return false;
		});
		$(".contextmenu").contextmenu(function(e){
			return false;
		});
		$(".fileber").contextmenu(function(e){
			return false;
		});
		$("#rename").keypress(function(e){
			if(e.keyCode == 13){
				var type = $(".editorBox .folderber > .rename").attr("type");
				var static;
				switch(type){
					case "file":
						static = Folder.Refilename($(this).attr("data"),this.value);
					break;
					case "folder":
						static = Folder.Refoldername($(this).attr("data"),this.value);
					break;
					case "create":
						static = Folder.Create($(this).attr("data"),this.value);
					break;
				}
				if(static){
					this.CloseRename();
				}else console.log("input提交失败");
			}
		});
	}
	this.File = function(data,div){
		for(var i in data){
			switch(data[i].type){
				case "folder":{
					var li = $("<li><div class='list' link='"+data[i].link+"'></div><ul></ul></li>").appendTo(div);
					var list = li.find('.list').click(function(){Folder.Drop(this)}).mouseup(function(e){
						var link = $(e.delegateTarget).attr("link");
						if(e.which == 3){
							var menu = $(".editorBox > .contextmenu").attr("link",link);
							menu.find("span").remove();
							for(var k in Config["ContextMenu"]["folderBer"]["folder"]){
								$("<span>"+k+"</span>").mouseup(function(){
									Config["ContextMenu"]["folderBer"]["folder"][$(this).text()]($(this.parentNode).attr("link"));
								}).appendTo(menu);
							}
							menu.css("top",e.clientY).css("left",e.clientX).show();
						}
					});
					$("<img src='../static/src/open.svg' draggable='false'><img src='../static/src/folder.svg' draggable='false'><span>"+data[i].name+"</span>").appendTo(list);
					var ul = li.find("ul");
					arguments.callee(data[i].data,ul);
				}
				break;
				case "file":{
					var file = data[i].name.split('.').pop().toLowerCase();
					file = Config['Folder'][file]?Config['Folder'][file]:Config['Folder']['file'];
					var list = $("<li><div class='list'></div></li>").appendTo(div).find(".list").attr("link",data[i].link).attr("name",data[i].name).click(function(){Folder.Open(this)}).mouseup(function(e){
						var link = $(e.delegateTarget).attr("link");
						if(e.which == 3){
							var menu = $(".editorBox > .contextmenu").attr("link",link);
							menu.find("span").remove();
							for(var k in Config["ContextMenu"]["folderBer"]["file"]){
								$("<span>"+k+"</span>").mouseup(function(){
									Config["ContextMenu"]["folderBer"]["file"][$(this).text()]($(this.parentNode).attr("link"));
								}).appendTo(menu);
							}
							menu.css("top",e.clientY).css("left",e.clientX).show();
						}
					});
					$("<div></div><img src='"+file+"' draggable='false'><span>"+data[i].name+"</span>").appendTo(list);
				}
				break;
				case "project":
					var li = $("<li><div class='list' link='"+data[i].link+"'></div><ul></ul></li>").appendTo(div);
					var list = li.find('.list').click(function(){Folder.Drop(this)}).mouseup(function(e){
						var link = $(e.delegateTarget).attr("link");
						if(e.which == 3){
							var menu = $(".editorBox > .contextmenu").attr("link",link);
							menu.find("span").remove();
							for(var k in Config["ContextMenu"]["folderBer"]["main"]){
								$("<span>"+k+"</span>").mouseup(function(){
									Config["ContextMenu"]["folderBer"]["main"][$(this).text()]($(this.parentNode).attr("link"));
								}).appendTo(menu);
							}
							menu.css("top",e.clientY).css("left",e.clientX).show();
						}
					});
					$("<img src='../static/src/open.svg' draggable='false'><img src='../static/src/folder.svg' draggable='false'><span>"+data[i].name+"</span>").appendTo(list);
					var ul = li.find("ul");
					arguments.callee(data[i].data,ul);
				break;
			}
		}
	}
	this.Drop = function(div){
		var static = $(div).parent().children("ul").css("display") == "none"?false:true;
		if(Key["ALT"]){
			if(static)$(div).parent().find("ul").hide().parent().find(".list > img:first").attr("src","../static/src/close.svg");
			else $(div).parent().find("ul").show().parent().find(".list > img:first").attr("src","../static/src/open.svg");
		}else{
			if(static)$(div).parent().children("ul").hide().parent().find(".list > img:first").attr("src","../static/src/close.svg");
			else $(div).parent().children("ul").show().parent().find(".list > img:first").attr("src","../static/src/open.svg");
		}
	}
	this.Open = function(div){
		var data = {
			link:$(div).attr("link"),
			name:$(div).attr("name")
		}
		var name = data.name;
		var type = data.name.split('.').pop().toLowerCase();
		type = Config['Folder'][type]?Config['Folder'][type]:Config['Folder']['file'];
		var div = $(".fileber > .filelist > div.file[link='"+data.link+"']");
		if(div.length != 0){
			div.trigger("click");
		}else{
			var div = $("<div class='file'></div>").attr("link",data.link).attr("name",name).click(function(e){
				if(!(e&&e.target.className == "end")){
					Editor.OptionsEvent(this);
				}
			}).appendTo(".editorBox .fileber .filelist").trigger("click");
			$("<img src='"+type+"' draggable='false'><span>"+name+"</span>").appendTo(div);
			$("<img class='end' src='../static/src/end.svg' draggable='false'>").click(function(){Folder.Close(this.parentNode)}).appendTo(div);
			
			Editor.ReName(name);

		}
	}
	this.Close = function(div){
		var name = $(div).attr("name");
		var last = $(div).prev();
		var next = $(div).next();
		$(div).remove();
		if($(".fileber .active").length == 0){
			if(last.length != 0){
				last.trigger("click");
			}else{
				next.trigger("click");
			}
		}

		Editor.ReName(name);

		Editor.Close($(div).attr("link"));
	}

	this.Refilename = function(path,newname){
		var file = path.split("/");
		var oldname = file.pop();
		var static = false;
		file = file.join("/");
		if(oldname != newname){
			var name = file+"/"+newname;
			console.log("重命名 "+path+" 为\r"+name);
			
			static = Data.ReName(path,name);

			if(static){
				var div = $(".editorBox .folderber li .list[link='"+path+"']");
				div.attr("link",name).attr("name",newname).find("span").text(newname);
				var option = $(".editorBox .fileber .filelist .file[link='"+path+"']");
				option.attr("link",name).attr("name",newname).find("span").text(newname);
				
				//添加新名字的重命名效果
				Editor.ReName(newname);

				//去掉旧名字的重命字效果
				Editor.ReName(oldname);
			}

		}else static = true;
		return static;
	}
	this.CloseRename = function(){
		$(this).val("");
		$(".editorBox .folderber > .rename").hide();
	}
	this.Refoldername = function(path,newname){
		var file = path.split("/");
		var oldname = file.pop();
		var static = false;
		file = file.join("/");
		if(oldname != newname){
			var name = file+"/"+newname;
			console.log("重命名 "+path+" 为\r"+name);
			
			static = Data.ReName(path,name);
			if(static){
				//更改目录下相关联的路径
				this.RePath(path,name);
			}

		}else static = true;
		return static;
	}
	this.RePath = function(oldpath,newpath){
		//目录下
		var oldname = oldpath.split("/").pop();
		var newname = newpath.split("/").pop();
		var div = $(".editorBox .folderber li .list[link='"+oldpath+"']");
		div.attr("link",newpath).find("span").text(newname);
		var divs = div.next().find(".list[link^='"+oldpath+"']");
		for(var i = 0;i < divs.length;i++){
			var path = $(divs[i]).attr("link");
			$(divs[i]).attr("link",path.replace(oldpath,newpath));
		}
		//选项卡下
		var divs = $(".editorBox .fileber .filelist .file[link^='"+oldpath+"']");
		for(var i = 0;i < divs.length;i++){
			var path = $(divs[i]).attr("link");
			$(divs[i]).attr("link",path.replace(oldpath,newpath));
		}
	}
	this.Delete = function(path){
		console.log("删除："+path);
		var static = Data.DeletePath(path);
		if(static){
			this.DeletePath(path);
		}else{
			console.log("删除失败");
		}
	}
	this.DeletePath = function(path){
		$(".editorBox .folderber li .list[link='"+path+"']").parent().remove();
		$(".editorBox .fileber .filelist .file[link='"+path+"'] .end").trigger("click");
	}
	this.Create = function(path,name){
		link = path+'/'+name;
		var static = Data.CreatePath(link);
		if(static){
			this.CreatePath(link);
		}
		return static;
	}
	this.CreatePath = function(path){
		var link = path;
		var path = path.split("/");
		var name = path.pop();
		path = path.join("/");
		if(name.indexOf(".") != -1){//文件
			var data = [{
				type:"file",
				name:name,
				link:link
			}];
			console.log(data);
			Folder.File(data,$(".editorBox .folderber li .list[link='"+path+"']").next());
			$(".editorBox .folderber li .list[link='"+path+"/"+link+"'").trigger("click");
		}else{//文件夹
			var data = [{
				type:"folder",
				name:name,
				link:link,
				data:[]
			}];
			Folder.File(data,$(".editorBox .folderber li .list[link='"+path+"']").next());
		}
	}
	this.MovePath = function(fromPath,toPath){
		// if()
		// this.CreatePath(toPath);
		var link = toPath;
		var path = toPath.split("/");
		var name = path.pop();
		path = path.join("/");
		console.log(link,path,name);
		console.log(fromPath);
		if($(".folderlist .list[link='"+link+"']").length == 0){
			target = $(".folderlist .list[link='"+path+"']").parent().children("ul");
			var div = $(".folderlist .list[link='"+fromPath+"']").parent();
			var divs = div.find(".list");
			for(var i = 0;i < divs.length;i++){
				let oldpath = $(divs[i]).attr("link");
				$(divs[i]).attr("link",oldpath.replace(fromPath,toPath));
				if(oldpath == fromPath){
					var name = toPath.split("/").pop();
					$(divs[i]).attr("name",name);
					$(divs[i]).find("span").text(name);
				}
			}
			div.appendTo(target);
			//刷新选项卡路径
			var divs = $(".editorBox .fileber .filelist .file[link^='"+fromPath+"']");
			for(var i = 0;i < divs.length;i++){
				let oldpath = $(divs[i]).attr("link");
				$(divs[i]).attr("link",oldpath.replace(fromPath,toPath));
				if(oldpath == fromPath){
					var name = toPath.split("/").pop();
					$(divs[i]).attr("name",name);
					$(divs[i]).find("span").text(name);
				}
			}
		}else{
			//以后可以弄个提示   （是否覆盖？）
		}
		
	}
	this.CopyPath = function(fromPath,toPath){
		var link = toPath;
		var path = toPath.split("/");
		var name = path.pop();
		path = path.join("/");
		console.log(link,path,name);
		if($(".folderlist .list[link='"+link+"']").length == 0){
			target = $(".folderlist .list[link='"+path+"']").parent().children("ul");
			var div = $(".folderlist .list[link='"+fromPath+"']").parent().clone(true);
			var divs = div.find(".list");
			for(var i = 0;i < divs.length;i++){
				let oldpath = $(divs[i]).attr("link");
				$(divs[i]).attr("link",oldpath.replace(fromPath,toPath));
				if(oldpath == fromPath){
					var name = toPath.split("/").pop();
					$(divs[i]).attr("name",name);
					$(divs[i]).find("span").text(name);
				}
			}
			div.appendTo(target);

		}else{
			//以后可以弄个提示   （是否覆盖？）
		}
	}
	this.FileStatus = function(userId,newPath,oldPath){
		console.log(userId,Data.UserData.id);
		var status =  Data.UserData.id == userId?'open':'used';
		$(".folderlist .list[link='"+newPath+"']").children('div').addClass(status);
		$(".folderlist .list[link='"+oldPath+"']").children('div').removeClass();
	}
	this.FileStatusList = function(data){
		for(var i = 0;i < data.length;i++){
			$(".folderlist .list[link='"+data[i]+"']").children('div').addClass("used");
		}
	}
}
var Folder = new Folder();
function Editor(){
	this.data = [];
	this.Init = function(){
		$(".editorBox .fileber")[0].onmousewheel = function(e){
			var left = $(".editorBox .fileber").scrollLeft();
			if(e.deltaY > 0){
				$(".editorBox .fileber").scrollLeft(left+180);
			}else if(e.deltaY < 0){
				$(".editorBox .fileber").scrollLeft(left-180);
			}
		};
	}
	this.OptionsEvent = function(div){
		$(".editorBox .fileber .filelist").find(".active").removeClass("active");
		$(div).addClass("active");
		Editor.Open($(div).attr("link"));
	}
	this.Open = function(link){
		var type = link.split(".").pop();
		type = "ace/mode/"+(Config.Mode[type]?Config.Mode[type]:Config.Mode['file']);
		var data = Data.OpenPath(link);
		console.log(data);
		data = data.slice(0,data.length-1);
		this.data[0].session.setMode(type);
		this.data[0].setValue(data,1);
		this.data[0].focus();
	}
	this.Close = function(link){
		this.data[0].setValue("")
		console.log("关闭"+link);
	}
	this.Save = function(){
		var div = $(".fileber > .filelist > .active");
		if(div.length == 1){
			var path = div.attr("link");
			var data = Editor.data[0].getValue();
			console.log(Data.SavePath(path,data));
		}else{
			console.log("保存问题");
		}
	}
	this.ReName = function(name){
		var divs = $(".fileber > .filelist > div.file[name='"+name+"']");
		if(divs.length > 1){
			for(var i = 0;i < divs.length;i++){
				var title = $(divs[i]).attr("link");
				$(divs[i]).find("span").text(title);
			}
		}else if(divs.length == 1){
			var title = divs.attr("name");
			divs.find("span").text(title);
		}
	}

	this.Create = function(div){
		ace.require("ace/ext/language_tools");
		var editor = ace.edit(div);
		editor.session.setMode("ace/mode/html");
		editor.setTheme("ace/theme/monokai");
		editor.setFontSize(20);
		editor.setOptions({
			autoScrollEditorIntoView: false,
			enableBasicAutocompletion: true,
			enableSnippets: true,
			enableLiveAutocompletion: false
		});
		this.data.push(editor);
	}
}
var Editor = new Editor();
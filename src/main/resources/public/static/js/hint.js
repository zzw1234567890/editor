function Hint(){
	this.Init = function(){
		this.Div = $(".hintBox").first();
		// this.filemoveOpen(0,"/123");
		this.filemoveClose();
		$(".hintBox").contextmenu(function(e){
			return false;
		});
		$(".fileOperate .buttonBox button:nth-child(2)").on("click",function(){
			var path = $(".fileOperate > .title").attr("link");
			var target = $("#movePath").val();
			var n = $(".fileOperate > .title").attr("type");
			console.log(path,target);
			if(n == 0){
				//移动
				if(Data.ReName(path,target)){
					Folder.MovePath(path,target);
					Hint.filemoveClose();
				}else{
					console.log("移动失败");
				}

			}else if(n == 1){
				//复制
				if(Data.CopyPath(path,target)){
					Folder.CopyPath(path,target);
					Hint.filemoveClose();
				}else{
					console.log("复制失败");
				}
			}
		});
		$(".fileOperate .buttonBox button:nth-child(1)").click(function(){
			Hint.filemoveClose();
		})
	}
	this.open = function (){
		this.Div.show();
	}
	this.close = function (){
		this.Div.hide();
	}
	this.filemoveOpen = function(n,path){
		this.open();

		title = n == 1?"复制":"移动";
		$(".fileOperate > .title").attr("link",path).text(title + " " + path);
		$(".fileOperate > .title").attr("type",n);
		var folder = $(".folderber .folderlist > ul").clone();
		$("#movePath").val(folder.find('.list').first().attr('link')+"/"+path.split("/").pop());
		folder.find("div[name]").parent().remove();
		folder.find(".list[link='"+path+"']").parent().remove();
		folder.find(".list").find("img:first").click(function(){
			Folder.Drop(this.parentNode);
		});
		folder.find(".list").find("span").click(function(){
			var path = $(".fileOperate > .title").attr("link");
			$("#movePath").val($(this).parent().attr('link')+"/"+path.split("/").pop())
		});
		folder.appendTo(".hintBox .folderBox");

		this.Div.find(".fileOperate").show();
	}
	this.filemoveClose = function(){
		this.close();
		this.Div.find(".fileOperate").hide();
		$(".hintBox .folderBox").children().remove();
	}
}
var Hint = new Hint();
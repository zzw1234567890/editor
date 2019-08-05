function Menu(){
	this.static = false;

	this.Init = function(){
		var menu = Config["Menu"];
		var that = this;
		$(".editorBox").click(function(){});
		for(var i in menu){
			if(menu[i].link){
				$("<span>"+i+"</span>").click(menu[i].link).appendTo(".editorBox > .menuber");
			}else{
				$("<span>"+i+"</span>").click(function(){that.static = !that.static;that.Drop(this);}).mouseenter(function(){that.Drop(this)}).appendTo(".editorBox > .menuber");
			}
		}
		$(".menuber").contextmenu(function(e){
			return false;
		});
		$("body").mouseup(function(e){
			if(e.target.className != "open"){
				that.static = false;
				that.Drop();
			}
			if(!(e.which == 3 && (e.target.className == "list" || e.target.parentNode.className == "list"))){
				$(".editorBox > .contextmenu").hide();
			}
		});
	}
	this.Drop = function(div){
		$(".editorBox > .menuber > .open").removeClass("open");
		if(Menu.static){
			var i = $(".editorBox > .menuber > span").index(div);
			$(".editorBox > .menuber > .dropdown").css("left",100*i+"px");
			$(div).addClass("open");
			var s = $(div).text();
			var data = Config["Menu"][s];
			$(".editorBox > .menuber > .dropdown > .list").remove();
			for(var i in data){
				$("<div class='list'><span>"+i+"</span><span>"+data[i].key+"</span></div>").click(data[i].link).appendTo(".editorBox > .menuber > .dropdown");
			}
			$(".editorBox > .menuber > .dropdown").show();
		}else{
			$(".editorBox > .menuber > .dropdown").hide();
		}
	}
	this.Exit = function(){
		console.log("退出");
	}
}
var Menu = new Menu();
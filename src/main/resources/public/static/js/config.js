var Config = {
	Menu:{
		"文件":{
			"保存":{
				key	:'Ctrl+s',
				link:Editor.Save
			},
		},
		"项目":{
			"运行":{
				key	:'Ctrl+r',
				link:function(){
					console.log("怎么运行，你都没告诉我！");
				}
			}
		},
		"插件":{
			"打开":{
				key	:'Ctrl+o',
				link:Menu.Open
			}
		},
		"配置":{
			"配置":{
				key:'Ctrl+p',
				link:function(){console.log("别看了，没有配置")}
			}
		},
		"关于":{
			"关于":{
				key:"",
				link:function(){console.log("关于");}
			}
		},
		"退出":{
			key:'',
			link:Menu.Exit
		}
	},
	Folder:{
		"html":'../static/src/HTML.svg',
		"css":'../static/src/CSS.svg',
		'file':'../static/src/CSS.svg'
	},
	ContextMenu:{
		"menuber":{

		},
		"folderBer":{
			"folder":{
				"新建":function(path){
					console.log("在"+path+"新建文件或文件夹");
					$("#rename").attr("data",path).val("");
					$(".editorBox .folderber > .rename").attr("type","create").show().find("span").text("名字");
					$("#rename").focus();
				},
				"移动":function(path){
					Hint.filemoveOpen(0,path);
				},
				"复制":function(path){
					Hint.filemoveOpen(1,path);
				},
				"重命名":function(path){
					console.log("重命名："+path);
					$("#rename").attr("data",path).val(path.split("/").pop());
					$(".editorBox .folderber > .rename").attr("type","folder").show().find("span").text("重命名");
					$("#rename").focus().select();
				},
				"删除文件夹":function(path){
					Folder.Delete(path);
				},
			},
			"file":{
				"移动":function(path){
					Hint.filemoveOpen(0,path);
				},
				"复制":function(path){
					Hint.filemoveOpen(1,path);
				},
				"重命名":function(path){
					console.log("重命名："+path);
					$("#rename").attr("data",path).val(path.split("/").pop());
					$(".editorBox .folderber > .rename").attr("type","file").show().find("span").text("重命名");
					$("#rename").focus().select();
				},
				"删除文件":function(path){
					Folder.Delete(path);
				},
			},
			"main":{
				"新建":function(path){
					console.log("在"+path+"新建文件或文件夹");
					$("#rename").attr("data",path).val("");
					$(".editorBox .folderber > .rename").attr("type","create").show().find("span").text("名字");
					$("#rename").focus();
				},
				"刷新(禁用)":function(){console.log("别点了，没用的");},
			}
		},
		"fileber":{

		}
	},
	Mode:{
		'html'	:'html',
		'css'	:'css',
		'js'	:'javascript',
		'php'	:'php',
		'file'	:'text',

	}
}
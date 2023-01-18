$(function(){
	// セレクトボックスが変更されたらテンプレートを切り替える
	$('#templateSelect').on("change",function(){
		var templateId = $('#templateSelect option:selected').val();
		$('#selectTemplateForm [name=templateId]').val(templateId);
		$('#selectTemplateForm').submit();
	});
	// アラートを表示後、3秒でフェードアウトさせる
	var fadeOutAlert = function(){
		$(".flash").fadeOut("slow");
	}
	setTimeout(fadeOutAlert, 3000);
	// 性格のpopover
	$('[data-toggle="popover"]').popover();
	
	// テンプレート選択ボタンが押下された時の処理
	$('#selectTemplateButton').on("click",function(){
		$(this).prop('disabled',true);
		$('#selectTemplateFormForUpdate').submit();
	});
	
	// 新規テンプレート登録ボタンが押下された時の処理
	$('#registerTemplateButton').on("click",function(){
		let url = location.href;
		let index = url.indexOf("?");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("?"));
		}
		url = url + "/register";
		window.location.href = url;
	});
	
	// テンプレート編集ボタンが押下された時の処理
	$('#editTemplateButton').on("click",function(){
		let url = location.href;
		let index = url.indexOf("?");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("?"));
		}
		let templateId = $('#deleteTemplateForm [name=templateId]').val();
		let version = $('#deleteTemplateForm [name=version]').val();
		url = url + "/edit?templateId=" + templateId + "&version=" + version;
		window.location.href = url;
	});
	
});
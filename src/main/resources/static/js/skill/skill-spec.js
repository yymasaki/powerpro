$(function() {

	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();
	}
	stopload();

	$('#delete-btn').on('click', function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
	});

	$(".ut-div").each(function(){
		$(this).find(".comma:last").remove();
	});

	// 入力値チェックでエラーが出た場合はモーダルを表示
	if (document.getElementById("error-comment") != null) {
		$("#delete-spec").modal();
	}

	if(document.getElementById("request-spec-btn") != null) {
		$("#edit-spec-btn").css("pointer-events", "none").css("opacity", 0.8);
	}

	// フラッシュメッセージを消す
	setTimeout(() => {
		$('#flash').alert('close');
	}, 3000);

	//開発経験の選択を10個に制限
	$('.limit10').change(function() {
		var selectedDev = $(".limit10 option:selected");
		if(selectedDev.length == 0) {
			$("#download-btn").prop("disabled", true);
			$(".error1").css("color", "red");
			$(".error2").removeAttr("style");
		}else if (selectedDev.length > 10) {
			$("#download-btn").prop("disabled", true);
			$(".error1").removeAttr("style");
			$(".error2").css("color", "red");
	  }else{
			$("#download-btn").prop("disabled", false);
			$(".error1").removeAttr("style");
			$(".error2").removeAttr("style");
	  }
	});

	setTimeout(() => {
		$(".ms-list").css("height","270px");
	}, 300);

	$("#download-form").submit(function(){
		$("#download-spec").modal('hide');
	});

	$("#dl-modal-btn").hover(function(){
		$(".ms-elem-selectable").removeClass("ms-hover").removeClass("ms-selected").css("display","list-item");
		$(".ms-elem-selection").removeClass("ms-selected").css("display","none");
		$(".option-dev").removeAttr('selected');
	});

	$("#comment").keyup(function(){
		var comment = $("#comment").val();
		comment = comment.replace(/[\s|　]+/g,'');
		if(comment == '') {
			$("#delete-btn").prop("disabled", true);
		}else {
			$("#delete-btn").prop("disabled", false);
		}
	});

	$("#download-form").on("keypress", function(e){
		if(e.which === 13){
			return false;
		}
	});

	$("#delete-form").on("keypress", function(e){
		if(e.which === 13){
			return false;
		}
	});

});

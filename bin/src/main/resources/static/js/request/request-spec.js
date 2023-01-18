$(function() {

	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	stopload();
	
	$('#process-btn').on('click', function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
	});
	
	$(".ut-div").each(function(){
		$(this).find(".comma:last").remove();
	});

	// フラッシュメッセージを消す
	setTimeout(() => {
		$('#flash').alert('close');
	}, 3000);

	$("#approval").click(function(){
		$(".process-name").text("承認");
		$(".note").remove();
		$("#reason-label").append('<small class="note">(任意)</small>');
		$("#hidden-stage").val("0");
		$("#process-btn").prop("disabled", false);
	});
	$("#disapproval").click(function(){
		$(".process-name").text("否承認");
		$(".note").remove();
		$("#reason-label").append('<small class="note" style="color:red">(必須)</small>');
		$("#comment").prop("required",true);
		$("#hidden-stage").val("3");
		$("#process-btn").prop("disabled", true);
	});

	$("#comment").keyup(function(){
		if($("#hidden-stage").val() == "3"){
			var comment = $("#comment").val();
			comment = comment.replace(/[\s|　]+/g,'');
			if(comment == '') {
				$("#process-btn").prop("disabled", true);
			}else {
				$("#process-btn").prop("disabled", false);
			}
		}
	});

	$("#process-form").on("keypress", function(e){
		if(e.which === 13){
			return false;
		}
	});

	$("#cancel-form").on("keypress", function(e){
		if(e.which === 13){
			return false;
		}
	});

});

$(function() {

	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	stopload();
	
	$('#submit-btn').on('click', function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
	});	
	
	$('[data-toggle="popover"]').popover();

	// フラッシュメッセージを消す
	setTimeout(() => {
		$('#flash').alert('close');
	}, 3000);

	$("#confirm-btn").hover(function(){
		$(".process-type").text("更新");
		$("#submit-btn").val(0);
	});
	$("#request-btn").hover(function(){
		$(".process-type").text("申請");
		$("#submit-btn").val(2);
	});

	$("#edit-form").on("keypress", function(e){
		if(e.which === 13 && document.activeElement.tagName != 'TEXTAREA'){
			return false;
		}
	});

	if($(".delete-dev").length == '2') {
		$(".delete-dev").hide();
	}

});

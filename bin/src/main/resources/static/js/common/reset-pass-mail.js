$(function(){
		
	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	
	stopload();
	
	// 送信ボタン１回押下したらボタン無効化する.処理中を表示.
	$('#done').on('click',function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
		$(this).prop('disabled',true);
		$('#emailForm').submit();
	});
	
	  $("input").on("keydown", function(e) {
		if ( e.which == 13 ) {
			$('.loading').show();
			setTimeout(stopload, 20000);
			
		}
	} );
		
});
$(function(){

	
	var hideAlert = function(){
		$("#flash").hide();
	}
	setTimeout(hideAlert, 3000);
	
	// 送信ボタンをクリック時の処理
    $('#exampleModalCenter').on('show.bs.modal',function(event){
        // 追加されるスキル数を取得
    	$('#title-name').text($('#title').val());
    	$('#content-name').html($('#content').val().replace(/\r?\n/g,'<br>'));
    	$('#post-start').text($('#startPostedOn').val());
    	$('#post-end').text($('#endPostedOn').val());
    	$('#department-name').text($('[name=departmentId] option:selected').text());
    	
        });
        
});
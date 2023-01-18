$(function(){
	// テンプレート削除ボタンが押下されたときの処理
	$('#deleteTemplateModalCenter').on('show.bs.modal',function(event){
		var templateName = $('#templateName').val();
		$('#template-name').text(templateName);
	});
	
	$('#done').on('click',function(){
		$(this).prop('disabled',true);
		$('#deleteTemplateForm').submit();
	});
});
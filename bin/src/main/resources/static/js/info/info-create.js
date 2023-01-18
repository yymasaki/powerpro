$(function(){
	$('#modal-open').prop('disabled',true);
	
	$('.infor').on('change',function(){
		var title=$('#title').val().replace(/^\s+/,"");
		var content=$('#content').val().replace(/^\s+/,"");
		var endPostedOn=$('#endPostedOn').val();
		
		if(!title || !content || !endPostedOn){
		$('#modal-open').prop('disabled',true);
		}else{
			$('#modal-open').prop('disabled',false);
		}
	});
	
	$('#done').on('click',function(){
		$(this).prop('disabled',true);
		$('#form').submit();
	});
});

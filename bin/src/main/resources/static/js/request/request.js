$(function() {
	
	 var applicant = $('#session-applicant').val();
	 $('.searchRequestForm [name=applicant]').val(applicant);
	 deniedHide();
	 
	// 非同期のメッセージを非表示
	var hideAlert = function() {
		$("#flash").hide();
	}
		
	setTimeout(hideAlert, 3000);
	
	//未承認数が0の時、一括承認は無効
	var stageValue= $('select#stage option:selected').attr('value');
	if($('#non-approval').text()== 0 || stageValue != 2){
	    $('#approval-all').prop('disabled',true);
	} else {
		$('#approval-all').prop('disabled',false);
	}
	
	//新規テクニカルスキル検索結果の画面での処理
	var contentValue=$('select#content option:selected').attr('value');
	if(contentValue==3){
		$('select#stage option[value=3]').hide();
	}else{
		$('select#stage option[value=3]').show();
	}
	
	// 新規テクニカルスキルでは否認非表示
	function deniedHide(){
		let stageValue=$('select#stage option:selected').attr('value');
		let contentValue=$('select#content option:selected').attr('value');
		if(contentValue==3){
			$('select#stage option[value=3]').hide();
		} else {
			$('select#stage option[value=3]').show();
		}
		if(stageValue==3){
			$('select#content option[value=3]').hide();
		} else {
			$('select#content option[value=3]').show();
		}
	}
	
	$('.check').on('change', function() {
		deniedHide();
	});
	
	//モーダルウィンドウの表示
	$('.modal-open').on('click',function(){
		let technicalSkillId=$(this).attr('id');
				
		let categoryName=$('#'+technicalSkillId+'category').text();
		let skillName=$('#'+technicalSkillId+'skill').text();
		
		$('#modalCategoryName').text(categoryName);
		$('#modalSkillName').text(skillName);
		
		//formのIDを一意にする
		$('#approvalForm').attr('id','approvalForm'+technicalSkillId);
		$('.modal-footer').attr('id',technicalSkillId);
				
	});
	
	//ダブルサブミット対策
    $('#all-done').on('click', function() {
       $(this).prop('disabled',true);
       $("#approvalAllForm").submit();
        
    });	
    $('#done').on('click', function() {
        $(this).prop('disabled',true);   
        let technicalSkillId=$(this).parents('.modal-footer').attr('id');
        
        $('#approvalForm'+technicalSkillId).submit();
     });
	
	
		
	var val = $('.searchRequestForm [name=stage]').val();
	$('#unapproved').hide();
	$('#denied').hide();
	$('#approved').hide();

	if (val === '2') {
		$('#unapproved').show();
	} else if (val === '3') {
		$('#denied').show();
	} else if (val === '0') {
		$('#approved').show();
	}
	
	// 1ページ目の場合、previousリンクを表示しない
	var currentPage = parseInt($('.searchRequestForm [name=pageNumber]').val());
	if (currentPage == 1) {
		$('.previous').hide();
	}
	
	$('.previous').on('click', function() {
		$('.searchRequestForm [name=pageNumber]').val(currentPage - 1);
		$('.searchRequestForm').submit();
	});

	$('.next').on('click', function() {
		$('.searchRequestForm [name=pageNumber]').val(currentPage + 1);
		$('.searchRequestForm').submit();
	});
	
	$('#searchButton').on('click', function() {
		$('.searchRequestForm [name=pageNumber]').val(1);
		$('.searchRequestForm').submit();
	});
	
	$('.status-request-tr').on('click',function(){
		let url = location.href;
		let statusId = $(this).data('status_id');
		let index = url.indexOf("?");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("?"));
		}
		url = url + "/status?statusId=" + statusId;
		window.location.href = url;
	});
	
	$('.specsheet-request-tr').on('click',function(){
		let url = location.href;
		let specsheetId = $(this).data('specsheet_id');
		let userId = $(this).data('user_id');
		let stage = $(this).data('stage');
		let index = url.indexOf("?");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("?"));
		}
		url = url + "/spec?specsheetId=" + specsheetId + "&userId=" + userId + "&stage=" + stage;
		window.location.href = url;
	});
});
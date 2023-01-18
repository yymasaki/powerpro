$(function(){
	//初期表示でボタン無効化
	$('.modal-open').prop('disabled',true);	

	//Enterキーの無効化
    $("input").on("keydown", function(e) {
        if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
            return false;
        } else {
            return true;
        }
    });
    
	//ダブルサブミット対策
    $("#done").on("click", function() {
       $(this).prop('disabled',true);
       $("#requestItemForm").submit();
        
    });
   
    // JQuery-validation-engineの設定
    jQuery("#requestItemForm").validationEngine({
      scrollOffset: 70
   });   
    
  //重複の確認を行う
	$('.check').on("change keyup", function() {
		//現在のURLから?technicalSkill~以降を除き、重複チェックメソッドのパスと繋げる.
		var nowUrl = location.href;
		var splitUrl=nowUrl.split("?");
		var url=splitUrl[0];
		url = url+"/check-item-edit";
		var category=$('.category-value option:selected').attr('value');
		  console.log(category);
		  var name=$('.skill-value').val();
		  console.log(name);
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				category: category,
				name:name
			},
			async: true // 非同期で処理を行う
		}).done(function(data) {
			// コンソールに取得データを表示
			console.log(data);
			console.dir(JSON.stringify(data));
			$("#checkMessage").html(data.checkMessage);
			
			if(data.checkMessage!=''||$('.skill-value').val()==''){
				$('.modal-open').prop('disabled',true);
				return false;
				}else if(data.checkMessage==''&&$('.skill-value').val()!='') {
	    		$('.modal-open').prop('disabled',false);
				}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました！");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus     : " + textStatus);
			console.log("errorThrown    : " + errorThrown.message);
		});
	});			
	
	//モーダルウィンドウに入力内容を表示する
	$('#exampleModal').on('show.bs.modal', function (event) {
		  //入力した内容を表示させる
		  var categoryName=$('.category-value option:selected').text();
		  var skillName=$('.skill-value').val();
		  skillName = skillName.replace(/\s+/g, "");
		  $('#category-name').text(categoryName);
		  $('#skill-name').text(skillName);
		    
		});
});
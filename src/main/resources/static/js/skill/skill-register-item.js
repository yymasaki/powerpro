	$(function() {		

		//メッセージ表示関連
		var hideAlert = function(){
			$("#flash").hide();
		}
		setTimeout(hideAlert, 3000);
		
	    //初期表示では登録ボタンを無効化
	    $('#modal-open').prop('disabled',true);
		
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
           $("#registerItemForm").submit();
            
        });
        
        // JQuery-validation-engineの設定
        jQuery("#registerItemForm").validationEngine({
           scrollOffset: 70
           });   
		        
	    //追加ボタン
	    $('#add').click(function() {
	    	copy();
	    });    

	    //削除ボタン
 	    $('.del').click(function() {
	        
 	    	var item=document.getElementsByClassName("item");
	        //初期画面内での処理
 	    	if(item.length>1) {
	    	   $(this).parents(".item").remove();
	       	 }

	        //番号振り直し
	        renumber();
	        }); 
		
 	   //クローンする関数
	    var copy = function(i) {
	        //クローンを変数に格納
	        var clonecode = $('.item:last').clone(true);

	        //数字を＋1し変数に格納
	        var num = clonecode.attr('data-count');
	        var num1 = parseInt(num) + 1;
	        //data属性の数字を＋１
	        clonecode.attr('data-count',num1);
	        
	        //セレクトボックスのname属性の数字を+1
	        var namecode = clonecode.find('select.category').attr('name');
	        namecode = namecode.replace(/technicalSkillList\[[0-9]{1,2}/g,'technicalSkillList[' + num1);
	        clonecode.find('select.category').attr('name',namecode);
	        console.log(namecode);
	        
	        //テキストフォームのname属性の数字を+1
	        var namecode = clonecode.find('input.skillName').attr('name');
	        namecode = namecode.replace(/technicalSkillList\[[0-9]{1,2}/g,'technicalSkillList[' + num1);
	        clonecode.find('input.skillName').attr('name',namecode);
	        
	        //カテゴリ名タイトル前の数字を+1
	        clonecode.find('#categoryCount').text(num1+1+'.カテゴリ名');
	        
	        //初期表示
	        clonecode.find('input.skillName').val('');
	        clonecode.find("#checkMessage").text('');
	        
	        //HTMLに追加
	        clonecode.insertAfter($('.item:last'));
	    };
 	    
 	    	//番号振り直しをする関数
			var renumber = function(i) {
	        var count = 0;

	        $('.item').each(function(){
	            //data属性の数字
	            $(this).attr('data-count',count);

	            var name = $('select.category',this).attr('name');
	            name = name.replace(/technicalSkillList\[[0-9]{1,2}/g,'technicalSkillList[' + count);
	            $('select.category',this).attr('name',name);

	            var name = $('input.skillName',this).attr('name');
	            name = name.replace(/technicalSkillList\[[0-9]{1,2}/g,'technicalSkillList[' + count);
	            $('input.skillName',this).attr('name',name);
	            
		        $('#categoryCount',this).text(count+1+'.カテゴリ名');
	            count += 1;
	        })
			}
			
			//DBとの重複の確認を行う
			$('.check').on("change keyup", function() {
				var url = location.href
				url = url+"/check-item";
				var element=$(this).parents(".item");
				var category=element.find('select.category option:selected').attr('value');
		    	console.log(category);
		    	var name=element.find('input.skillName').val();
		    	console.log(name);
		    	var count=0;
				$.ajax({
					url : url,
					type : 'GET',
					dataType : "json",
					data : {
						name:name,
						category: category
					},
					async: true // 非同期で処理を行う
				}).done(function(data) {
					// コンソールに取得データを表示
					console.log(data);
					console.dir(JSON.stringify(data));
					element.find("#checkMessage").html(data.checkMessage);
					
			    	$('.item').each(function(){
				    	var element=$(this);
				    	if(element.find('input.skillName').val()==''||
				    		element.find('#checkMessage').text()!=''){
				    		$('#modal-open').prop('disabled',true);	
				    		return false;
				    	}else if(element.find('input.skillName').val()!=''&&element.find('#checkMessage').text()=='') {
				    		$('#modal-open').prop('disabled',false);
				    	}     
				    	});
					
				}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
					alert("エラーが発生しました！");
					console.log("XMLHttpRequest : " + XMLHttpRequest.status);
					console.log("textStatus     : " + textStatus);
					console.log("errorThrown    : " + errorThrown.message);
				});
			});
			
			// 送信ボタンをクリック時の処理
		    $('#exampleModalCenter').on('show.bs.modal',function(event){
		    	
		        // 追加されるスキル数を取得
		        var itemIndex=parseInt($('.item:last').attr('data-count'));
		        console.log(itemIndex);
		        	
		        	var firstItem=$('.item:first');
		        	var categoryName=$('.category option:selected',firstItem).text();
		        	var skillName=$('.skillName',firstItem).val();
		        	skillName = skillName.replace(/\s+/g, "");
		        	$('#category-name').text('1.カテゴリ名：'+categoryName);
		            $('#register-name').text(skillName);
		        if(itemIndex>0){
		        	for(i=1;i<=itemIndex;i++){
		        		var cloneConfirm=$('.confirm:last').clone(true);
		        		var item=$('.item').eq(i);
		        		var categoryName=$('.category option:selected',item).text();
		        	    var skillName=$('.skillName',item).val();
		        	    skillName = skillName.replace(/\s+/g, "");
		        		  cloneConfirm.find('#category-name').text(i+1+'.カテゴリ名：'+categoryName);
		        		  cloneConfirm.find('#register-name').text(skillName);
		                  // HTMLに追加
		                  cloneConfirm.insertAfter($('.confirm:last'));
		        	}
		        }
		        })
		        
		        // 戻るボタンを押した時の処理
		         $('#exampleModalCenter').on('hide.bs.modal',function(event){
		        	 var confirmIndex=parseInt($('.confirm').length)-1;
		        	 //クローンが存在する場合に削除
		        	 if(confirmIndex>0){
		        		 for(i=confirmIndex;i>0;i--){
		        			 $('.confirm:last').remove();
		        		 }
		        	 }
		         })
			
			
	 }); 	    
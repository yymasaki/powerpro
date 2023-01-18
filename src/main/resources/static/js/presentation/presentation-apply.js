$(function() {


	let file1 = $('#fileName1').text();
	let file2 = $('#fileName2').text();
	let file3 = $('#fileName3').text();

	//モーダルにform内容表示させる
	$('#modal-open').on('click', function() {
		
	let title = $('#title').val();
	let content = $('#content').val();
	let apply1 = $('#name1').text();
	let apply2 = $('#name2').text();
	let apply3 = $('#name3').text();
	let apply4 = $('#name4').text();
	let apply5 = $('#name5').text();
	let preferredMonth = $('#schedule').val();
	let preferredTerm = $('#preferredTerm').val();

	let file1 = $('#fileName1').text();
	console.log("file1これええ" + file1);
	let file2 = $('#fileName2').text();
	let file3 = $('#fileName3').text();
	let file4 = $('#file1').val();
	let file5 = $('#file2').val();
	let file6 = $('#file3').val();
	var array1 = file4.split('\\');
	var array2 = file5.split('\\');
	var array3 = file6.split('\\');



		$('#title-name').text(title);
		$('#content-name').text(content);
		$('#apply1-name').text(apply1);
		$('#apply2-name').text(apply2);
		$('#apply3-name').text(apply3);
		$('#apply4-name').text(apply4);
		$('#apply5-name').text(apply5);
		$('#preferred-month-name').text(preferredMonth);
		$('#preferred-term-name').text(preferredTerm);
		$('#document-name1').text(file1);
		$('#document-name2').text(file2);
		$('#document-name3').text(file3);

		$('#document-name4').text(array1[array1.length - 1]);
		$('#document-name5').text(array2[array2.length - 1]);
		$('#document-name6').text(array3[array3.length - 1]);



	})
	//一時保存時のstageセット
	$('#save-btn').on('click', function() {
		$('#apply-stage').val(1);
		fileMethod();
	})

	// 応募時のstageセット
	$('#apply-set-stage-button').on('click', function() {
		$('#apply-stage').val(2);
		fileMethod();
	})

	//file有無フラグのセット
	const fileMethod = function() {
		console.log("fileLog");
		let file1 = $('#file1').val();
		let file2 = $('#file2').val();
		let file3 = $('#file3').val();
		console.log(file1);


		if (file1 == "") {
			$('#fileFlag1').val('NoFile');
		}
		if (file2 == "") {
			$('#fileFlag2').val('NoFile');
		}
		if (file3 == "") {
			$('#fileFlag3').val('NoFile');
		}
	}

	if (file1.length == 0 && file2.length == 0 && file3.length == 0) {
		//fileの表示/非表示→余裕があればもっときれいに書く
		document.getElementById('file2').style.visibility = "hidden";
		document.getElementById('file3').style.visibility = "hidden";
		document.getElementById('add-document2').style.visibility = "hidden";

		$('#add-document1').on('click', function() {
			document.getElementById('file2').style.visibility = "visible";
			document.getElementById('add-document1').style.display = "none";
			document.getElementById('add-document2').style.visibility = "visible";
		})
		$('#add-document2').on('click', function() {
			document.getElementById('file3').style.visibility = "visible";
			document.getElementById('add-document2').style.display = "none";
		})
	} else {
		
		document.getElementById('add-document2').style.visibility = "hidden";
		document.getElementById('add-document1').style.visibility = "hidden";
		if (file3.length > 0) {
			document.getElementById('file1').style.visibility = "hidden";
			document.getElementById('file2').style.visibility = "hidden";
			document.getElementById('file3').style.visibility = "hidden";
		} else if (file2.length > 0) {
			document.getElementById('file2').style.visibility = "hidden";
			document.getElementById('file3').style.visibility = "hidden";
		} else {
			document.getElementById('file3').style.visibility = "hidden";
		}
	}
})

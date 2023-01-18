'use strict'

window.onpageshow = () => {

	let searchButton = document.getElementById('search-button');

	//発表者名
	let name1 = document.getElementById('name1');
	let name2 = document.getElementById('name2');
	let name3 = document.getElementById('name3');
	let name4 = document.getElementById('name4');
	let name5 = document.getElementById('name5');

	//発表者のID
	let apply1 = document.getElementById('apply1');
	let apply2 = document.getElementById('apply2');
	let apply3 = document.getElementById('apply3');
	let apply4 = document.getElementById('apply4');
	let apply5 = document.getElementById('apply5');

	//削除ボタン
	let deleteButton1 = document.getElementById('delete-button1');
	let deleteButton2 = document.getElementById('delete-button2');
	let deleteButton3 = document.getElementById('delete-button3');
	let deleteButton4 = document.getElementById('delete-button4');
	let deleteButton5 = document.getElementById('delete-button5');

	//ファイル削除ボタン
	let fileDeleteButton1 = document.getElementById('fileDeleteButton1');
	let fileDeleteButton2 = document.getElementById('fileDeleteButton2');
	let fileDeleteButton3 = document.getElementById('fileDeleteButton3');

	//メッセージ
	let presenterMessage = document.getElementById('presenter-message');
	presenterMessage.style.visibility = 'hidden';
	let noDataMessage = document.getElementById('no-data-message');
	noDataMessage.style.visibility = 'hidden';

	//一時保存のデータがあるかどうかの確認
	if (name1.textContent.length == 0) {
		deleteButton1.style.visibility = 'hidden';
	}
	if (name2.textContent.length == 0) {
		deleteButton2.style.visibility = 'hidden';
	}
	if (name3.textContent.length == 0) {
		deleteButton3.style.visibility = 'hidden';
	}
	if (name4.textContent.length == 0) {
		deleteButton4.style.visibility = 'hidden';
	}
	if (name4.textContent.length == 0) {
		deleteButton5.style.visibility = 'hidden';
	}

	let userNameList = document.getElementById('userNameList');

	//nameをListにセットする
	var names = [{ id: apply1, name: name1, delete: deleteButton1 }, { id: apply2, name: name2, delete: deleteButton2 },
	{ id: apply3, name: name3, delete: deleteButton3 }, { id: apply4, name: name4, delete: deleteButton4 }, { id: apply5, name: name5, delete: deleteButton5 }];

	searchButton.onclick = (e) => {
		const url = new URL('http://localhost:8080/presentation/searchPresenter');
		const name = document.getElementById('search-name').value;
		url.searchParams.append("name", name);

		//前の検索結果が残っていたら削除する
		let list = document.getElementById('userNameList');
		if (list.firstChild) {
			console.log('子要素あり');
			//削除
			while (list.firstChild) {
				list.removeChild(list.firstChild);
			}
		} else {
			console.log('なし');
		}
		presenterMessage.style.visibility = 'hidden';
		noDataMessage.style.visibility = 'hidden';

		var selectIds = [];
		fetch(url)
			.then(response => {
				return response.json();
			})
			.then(nameData => {

				console.log("取ってきたnameData" + nameData.length);
				if (nameData.length == 0) {
					noDataMessage.style.visibility = 'visible';
				}
				else {

					for (const user of nameData) {
						const tr = document.createElement('tr');
						userNameList.appendChild(tr);


						if (name1.textContent.match(user.name) || name2.textContent.match(user.name)
							|| name3.textContent.match(user.name) || name4.textContent.match(user.name)) {
						} else {

							//1行の中身入れる
							const tdDep = document.createElement('td');
							tdDep.textContent = user.department.name;
							tr.appendChild(tdDep);


							const tdName = document.createElement('td');
							tdName.textContent = user.name;
							tr.appendChild(tdName);

							const tdButton = document.createElement('td');
							tr.appendChild(tdButton)

							let button = document.createElement('button');
							tdButton.appendChild(button);
							button.textContent = '選択';
							button.value = user.userId;
							button.name = user.name;

							button.type = 'button';
							button.id = user.userId;
							selectIds.push(user.userId);


							console.log("name1のテキスト！" + name1.textContent);
							console.log("user.nameですよ" + user.name);
							console.log('ボタン' + button.id);

						}

					}

					selectIds.forEach((id) => {
						let selectButton = document.getElementById(id);
						selectButton.onclick = (e) => {
							//検索結果のモーダルを閉じる
							$('#searchModal').modal('hide');
							console.log("value:" + name1.textContent);

							//発表者がすでに４名登録されていたらエラーメッセージを出す
							if (name1.textContent.length != 0 && name2.textContent.length != 0 && name3.textContent.length != 0
								&& name4.textContent.length != 0 && name5.textContent.length != 0) {
								presenterMessage.style.visibility = 'visible';
							}
							names.some((name) => {
								if (name.name.textContent.length == 0) {
									name.name.textContent = selectButton.name;
									console.log('セットしたなまえ' + name.name.textContent);
									name.delete.style.visibility = 'visible';
									name.id.value = selectButton.value;
									console.log('idのvalue:' + name.id.value);
									return true;
								}
							})
							//ここで検索結果削除
							while (userNameList.firstChild) {
								userNameList.removeChild(userNameList.firstChild);
							}
							selectButton.parentNode.removeChild(selectButton);
						}
					})
				}


			})
			.catch(error => {
				console.error('通信失敗：' + error)
			})
	}

	//発表者を削除するときの挙動
	deleteButton1.onclick = (e) => {
		name1.textContent = "";
		apply1.value = null;
		deleteButton1.style.visibility = 'hidden';
	}
	deleteButton2.onclick = (e) => {
		name2.textContent = "";
		apply2.value = null;
		deleteButton2.style.visibility = 'hidden';
	}
	deleteButton3.onclick = (e) => {
		name3.textContent = "";
		apply3.value = null;
		deleteButton3.style.visibility = 'hidden';
	}
	deleteButton4.onclick = (e) => {
		name4.textContent = "";
		apply4.value = null;
		deleteButton4.style.visibility = 'hidden';
	}
	deleteButton5.onclick = (e) => {
		name5.textContent = "";
		apply5.value = null;
		deleteButton5.style.visibility = 'hidden';
	}

	var file1 = document.getElementById('file1');
	var file2 = document.getElementById('file2');
	var file3 = document.getElementById('file3');
	//fileName
	let fileName1 = document.getElementById('fileName1');
	let fileName2 = document.getElementById('fileName2');
	let fileName3 = document.getElementById('fileName3');


	var doc = {

		deleteDoc: function(fileName, deleteDocumentButton) {
			const url = new URL('http://localhost:8080/presentation/deleteDocument');
			var presentationId = document.getElementById('presentationIdDocument').value;
			url.searchParams.append('fileName', fileName.textContent);
			url.searchParams.append('presentationId', presentationId);
			fetch(url)
				.then(response => {
					return response.json();
				})
				.then(count => {
					console.log('戻ってきた値' + count);
					if (count == 1) {
						//fileName.style.visibility = 'hidden';
						fileName.textContent = "";
						deleteDocumentButton.style.visibility = 'hidden';
						
						
						console.log('削除しました');
						console.log(file1);
						console.log(file2);
						console.log(file3);
						console.log(fileName1);
						console.log(fileName2);
						console.log(fileName3);

						/** if (file2.style.visibility = "visible") {
							file3.style.visibility = "visible";
							console.log('1番の処理');
						} else if (file1.style.visibility = "visible") {
							file2.style.visibility = "visible";
							console.log('2番の処理');
						} else {
							file1.style.visibility = "visible";
							console.log('3番の処理');
						}*/

						if (fileName1.textContent != "") {
							if (fileName2.textContent != "") {
								file1.style.visibility = "visible";
							} else if (fileName3.textContent != "") {
								file1.style.visibility = "visible";
							} else {
								file2.style.visibility = "visible";
							}
						} else if (fileName2.textContent != "") {
							if (fileName3.textContent != "") {
								file1.style.visibility = "visible";
							} else {
								file2.style.visibility = "visible";
							}
						} else if (fileName3.textContent != "") {
							file1.style.visibility = "visible";
							file2.style.visibility = "visible";
						} else {
							file3.style.visibility = "visible";
						}
					}
				}).catch(error => {
					console.error('通信失敗：' + error)
				})
		}
	}

	//documentのdelete
	fileDeleteButton1.onclick = (e) => {
		//let fileName = document.getElementById('fileName1');
		doc.deleteDoc(fileName1, fileDeleteButton1);
	}

	fileDeleteButton2.onclick = (e) => {
		//var fileName = document.getElementById('fileName2');
		doc.deleteDoc(fileName2, fileDeleteButton2);
	}

	fileDeleteButton3.onclick = (e) => {
		//var fileName = document.getElementById('fileName3');
		doc.deleteDoc(fileName3, fileDeleteButton3);
	}






}
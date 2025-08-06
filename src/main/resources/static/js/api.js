

// OP① REST API呼び出し(文字列変換)
// OP⑤ REST API呼び出し後、SOAP通信(文字列変換)
document.getElementById('conversionForm').addEventListener('submit', async function(e) {
	e.preventDefault();
	const inputString = document.getElementById('inputString').value;

	const basePath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';

	fetch(`${basePath}/${apiPath}?inputString=${encodeURIComponent(inputString)}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			}			
		})
		//.then(response => response.json())
		.then(response => {
			if (!response.ok) {
				throw new Error('API呼び出しに失敗しました');
			}
			return response.text();
		})
		.then(data => {
			document.getElementById('resultText').textContent = data;
			document.getElementById('resultContainer').style.display = 'block';
			document.getElementById('errorContainer').style.display = 'none';
		})
		.catch(error => {
			document.getElementById('errorText').textContent = error.message;
			document.getElementById('errorContainer').style.display = 'block';
			document.getElementById('resultContainer').style.display = 'none';
		});

});


// OP② REST API呼び出し(JPG取得)
// OP⑥ REST API呼び出し後、SOAP通信(JPG取得)
async function loadImage() {
	const imageName = document.getElementById('imageName').value.trim();
	const errorElement = document.getElementById('error');
	const imagePreview = document.getElementById('imagePreview');

	errorElement.textContent = '';
	imagePreview.style.display = 'none';

	if (!imageName) {
		errorElement.textContent = '画像名前を入力してださい！';
		return;
	}

	try {
		const basePath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
		const response = await fetch(`${basePath}/${apiPath}?imageName=${encodeURIComponent(imageName)}`,  {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			}
		})

		if (!response.ok) {
			const error = await response.text();
			throw new Error(error || '画像取得が失敗しました');
		}

		const result = await response.json();
		if (!result.imageData) {
			throw new Error('取得した画像が表示できません');
		}

		const mimeType = result.mimeType || 'image/jpeg';
		imagePreview.src = `data:${mimeType};base64,${result.imageData}`;
		imagePreview.alt = result.imageName || 'Loaded image';
		imagePreview.style.display = 'block';



	} catch (error) {
		errorElement.textContent = error.message;
	}
}

// OP③ REST API呼び出し(PDF取得)	
// OP⑦ REST API呼び出し後、SOAP通信(PDF取得)
async function getPdf() {
	const status = document.getElementById('status');

	const basePath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
	fetch(`${basePath}/${apiPath}`, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/pdf',
		}
	})

		.then(response => {
			if (!response.ok) {
				throw new Error('PDFダウンロード失敗: ' + response.status);
			}
			return response.blob();
		})
		.then(blob => {

			const url = window.URL.createObjectURL(blob);
			const a = document.createElement('a');
			a.href = url;
			a.download = '絵で見て分かるJSBase開発.pdf';
			document.body.appendChild(a);
			a.click();

			window.URL.revokeObjectURL(url);
			document.body.removeChild(a);

			status.textContent = 'PDFダウンロードが完了しました。';
		})
		.catch(error => {
			status.textContent = error.message;
		});
}

// OP④ REST API呼び出し(ZIP取得)
// OP⑧ REST API呼び出し後、SOAP通信(ZIP取得)	
async function getZip() {
	const status = document.getElementById('status');

	const basePath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
	try {
		window.location.href = `${basePath}/${apiPath}`;

		status.textContent = 'ZIPダウンロードが完了しました。';
	} catch (error) {
		status.textContent = 'ZIPダウンロード失敗: ' + error.message;
	}

}

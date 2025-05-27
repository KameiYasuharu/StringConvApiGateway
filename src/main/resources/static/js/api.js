document.getElementById('conversionForm').addEventListener('submit', async function(e) {
	e.preventDefault();
	const inputString = document.getElementById('inputString').value;

	const basePath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';

	fetch(`${basePath}/ApiGateway?inputString=${encodeURIComponent(inputString)}`, {
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
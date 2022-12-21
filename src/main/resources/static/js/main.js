let inputImg = document.querySelector("#inputImg")
let img = document.querySelector("#displayImg")
let likeBtnList = document.getElementsByClassName("btn like-btn")
let likeCountList = document.getElementsByClassName("like-count")
let comment = document.getElementById("comment")
let cancelBtn = document.getElementById("cancel-comment-btn")
let homepageImage = document.getElementById("homepage-image")

if (homepageImage != null) {
	const imageArray = [
		"https://img3.oastatic.com/img2/74172964/max/t.jpg",
		"https://media.audleytravel.com/-/media/images/home/europe/italy/places/amalfi-coast/amalfi-coast-shutterstock_1436187389-1000x3000.jpg?q=79&w=1920&h=640",
		"https://media.cntraveller.com/photos/611bf7a0f6bd8f17556dbb02/16:9/w_2992,h_1683,c_limit/provence-in-southern-france-gettyimages-598180730.jpg",
		"https://cdn.tourradar.com/s3/tour/1500x800/85738_1e3935fa.jpg",
		"https://wallpaperaccess.com/full/194458.jpg",
		"https://tandktravelgroup.com/wp-content/uploads/2022/07/travel1.jpeg",
		"https://media.architecturaldigest.com/photos/57e2dba0f2e2598d338a04fe/16:9/w_2560%2Cc_limit/torres-del-paine-national-park.jpg"
	]

	let i = 0;

	setInterval(() => {
		i++;
		i = i % (imageArray.length);
		homepageImage.src = imageArray[i]
	}, 2500)
}

if (inputImg !== null) {
	inputImg.addEventListener("change", function() {
		const file = this.files[0]
		if (file) {
			const reader = new FileReader();
			reader.onload = function() {
				const result = reader.result;
				img.src = result;
			};
			reader.readAsDataURL(file);
		}
	});
}

let baseURL = "http://localhost:8000";
let deleteURL = "";
let accentColor = "limegreen"

function confirmDelete(element) {
	deleteURL = element.name;
	console.log(element);
	console.log(deleteURL);
}

function deletePost() {
	fetch(baseURL + deleteURL).then(() => {
		if (deleteURL.includes("/users/delete/") || deleteURL.includes("/categories/delete/")) {
			baseURL += "/dashboard/users";
		}
		else if (deleteURL.includes("/comments/delete/")) {
			baseURL += "/posts/" + deleteURL.split("/")[4];
		}
		window.location.href = baseURL;
	}).catch(() => console.log("an error occured"))
}

Array.from(likeBtnList).forEach(element => {
	const info = element.name.split("/")
	element.style.border = "1px solid " + accentColor;

	if (info[2] === "false") {
		element.style.background = "";
		element.style.color = accentColor;
		element.innerHTML = "Like <i class='fa fa-thumbs-up'></i>"
	}
	else {
		element.style.background = accentColor;
		element.style.color = "white";
		element.innerHTML = "Liked <i class='fa fa-thumbs-up'></i>"
	}
})

Array.from(likeCountList).forEach(e => console.log(e.innerHTML))


function reactpost(element) {
	console.log(element.name, " Liked")
	const targetIndex = Array.from(likeBtnList).indexOf(element)
	if (element.style.background == "") {
		element.style.background = accentColor;
		element.style.color = "white"
		element.innerHTML = "Liked <i class='fa fa-thumbs-up'></i>"
		likeCountList[targetIndex].innerHTML = parseInt(likeCountList[targetIndex].innerHTML) + 1
	}
	else {
		element.style.background = "";
		element.style.color = accentColor;
		element.innerHTML = "Like <i class='fa fa-thumbs-up'></i>"
		likeCountList[targetIndex].innerHTML = parseInt(likeCountList[targetIndex].innerHTML) - 1
	}
	fetch(baseURL + "/posts/like/" + element.name.replace("/false", "").replace("/true", "")).then(() => {
		console.log("request sent to: " + baseURL + "/posts/like/" + element.name.replace("/false", "").replace("/true", ""))
	});
}
console.log(comment)
function clearComment(element) {
	comment.value = "";
}

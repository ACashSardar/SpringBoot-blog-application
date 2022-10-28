let dateTime=document.getElementById("date-time");
let inputImg=document.querySelector("#inputImg")
let img=document.querySelector("#displayImg")
let likeBtnList=document.getElementsByClassName("btn like-btn")
let likeCountList=document.getElementsByClassName("like-count");

if(dateTime!==null){
	function getCurrentDateTime(){
		let currDateTime=new Date();
		dateTime.innerHTML=currDateTime.toDateString();
	}
	setTimeout(getCurrentDateTime,1);
}

if(inputImg!==null){
	inputImg.addEventListener("change",function(){
	    const file=this.files[0]
	    if(file){
	        const reader=new FileReader();
	        reader.onload=function(){
	            const result=reader.result;
	            img.src=result;
	        };
	        reader.readAsDataURL(file);
	    }
	});
}

let baseURL="http://localhost:8000";
let deleteURL="";
let accentColor="lightseagreen"

function confirmDelete(element){
	deleteURL=element.name;
	console.log(element);
	console.log(deleteURL);
}

function deletePost(){
	fetch(baseURL+deleteURL).then(()=>{
		if(deleteURL.includes("/users/delete/") || deleteURL.includes("/categories/delete/")){
			baseURL+="/dashboard/users";
		}
		window.location.href = baseURL;
	}).catch(()=>console.log("an error occured"))
}
	
Array.from(likeBtnList).forEach(element=>{
	const info=element.name.split("/")
	element.style.border="1px solid "+accentColor;
	
	if(info[2]==="false"){
		element.style.background="";
		element.style.color=accentColor;
		element.innerHTML="Like this post <i class='fa fa-thumbs-up'></i>"
	}
	else{
		element.style.background=accentColor;
		element.style.color="white";
		element.innerHTML="Liked <i class='fa fa-thumbs-up'></i>"			
	}
})

Array.from(likeCountList).forEach(e=>console.log(e.innerHTML))


function reactpost(element){
	console.log(element.name," Liked")
	const targetIndex=Array.from(likeBtnList).indexOf(element)
	if(element.style.background==""){
		element.style.background=accentColor;
		element.style.color="white"
		element.innerHTML="Liked <i class='fa fa-thumbs-up'></i>"
		likeCountList[targetIndex].innerHTML=parseInt(likeCountList[targetIndex].innerHTML)+1
	}
	else{
		element.style.background="";
		element.style.color=accentColor;
		element.innerHTML="Like this post <i class='fa fa-thumbs-up'></i>"
		likeCountList[targetIndex].innerHTML=parseInt(likeCountList[targetIndex].innerHTML)-1
	}
	fetch(baseURL+"/posts/like/"+element.name.replace("/false","").replace("/true","")).then(()=>{
		console.log("request sent to: "+baseURL+"/posts/like/"+element.name.replace("/false","").replace("/true",""))
	});
}


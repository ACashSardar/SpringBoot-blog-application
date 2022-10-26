let dateTime=document.getElementById("date-time");
let inputImg=document.querySelector("#inputImg")
let img=document.querySelector("#displayImg")

if(dateTime!==null){
	function getCurrentDateTime(){
		let currDateTime=new Date();
		dateTime.innerHTML=currDateTime.toDateString();
	}
	setTimeout(getCurrentDateTime,1);
	setInterval(getCurrentDateTime,1000000);
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





"use strict";

const inputEL = document.querySelector(".input-chat");
const btnEl = document.querySelector(".fa-paper-plane");
const cardBodyEl = document.querySelector(".card-body");

let userMessage;
const URL = "http://127.0.0.1:8080/chat";
//const URL = "http://127.0.0.1:8080/chat-rag";
//const URL = "http://127.0.0.1:8080/embed";
//const URL = "http://127.0.0.1:8080/embed/distance";

const chatGenerator = (robot) => {

    if (userMessage.length < 1)
        return;

    robot = robot.querySelector(".robot");
    let reponse = fetch(URL, {
        //mode: "no-cors",
        method: "POST",
        body: userMessage,
    })
    .then(response => response.text())
    .then(body => {
        console.log(body);
        robot.textContent = body;
    })
    .catch((error) => {
        robot.textContent = error;
    })
};



function manageChat() {
    userMessage = inputEL.value.trim();
    console.log(userMessage);

    if(!userMessage)
        return

    inputEL.value = "";

    cardBodyEl.appendChild(messageEl(userMessage, "user"));

    setTimeout(()=>{
        const robotMessage = messageEl("Thinking...", "chat-bot");
        cardBodyEl.append(robotMessage);
        chatGenerator(robotMessage);

    }, 600);
}


//message
const messageEl = (message, className) => { 
    const chatEl = document.createElement("div"); 
    chatEl.classList.add("chat", `${className}`);
    let chatContent = 
        className === "chat-bot" 
        ? `<span class="user-icon"><i class="fa fa-robot"></i></span><p class='robot'>${message}</p>` 
        : `<span class="user-icon"><i class="fa fa-user"></i></span><p>${message}</p>`
    chatEl.innerHTML = chatContent;
    return chatEl;
}

btnEl.addEventListener("click", manageChat);

/*
FIXME: does not work
inputEL.addEventListener("input", (e) => {
    e.preventDefault();
    e.target.addEventListener("keydown", (keyboard) => {
        if(keyboard.key === "Enter") {
            manageChat();
        }
    });
});*/
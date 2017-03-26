# IBM Watson JavaFX Project

This was an assignment from university. Where we had to create a Java application that uses IBM Watson's API. 



This project use’s 2 of IBM Watson API’s. The API’s I use are Personality Insights and the Tone analyser. 

My application allows the user to enter text into the text area or select a text file and then edit it in the text area. Then the user presses analyse and the text gets sent to IBM Watson, the returned JSON file gets parsed and the data is displayed in 2 pie charts, a bar chart and line chart inside a scroll viewer on the right side of the application.

The user can analyse text an unlimited number of times. 

The user even has the option to reset the application to the original load state but pressing the reset button in the menu bar at the top of the application. 

Here is a video demonstration of the web application - 

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/GNXpwUKDI3E/0.jpg)](https://www.youtube.com/watch?v=GNXpwUKDI3E)

## IBM Watson
_“Watson is an IBM supercomputer that combines artificial intelligence (AI) and sophisticated analytical software for optimal performance as a “question answering” machine. The supercomputer is named for IBM’s founder, Thomas J. Watson.”_

IBM Watson has a lot of API’s that can be used so in this project I use the personality insights API and the tone analyser API.

Both API’s work, in the same way, you send some text to IBM Watson and receive a JSON file containing all the results. 

## Use of Libraries
In this project, I used GSON to convert the returned JSON files to java objects. GSON is a java library created by google. 

_“Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.”_ 

## Graphical User Interface (GUI)
Before this term, I have never created a GUI in java. After messing around with swing, I decided to learn a bit of JavaFX to complete the assignment. The reason I decided to do this is because when I started to do some research into JavaFX it looks more efficient and cleaner than creating a GUI with swing. This because the layout/structure of the GUI is in one file (FXML) and the logic is attached to it through a controller class (MVC).

By doing it this way it would be a lot easier to change between layouts because all the logic is separated into a separate class (controller). 

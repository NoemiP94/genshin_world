# Genshin World 
 
This project allows users to manage in all its parts an application about Genshin Impact's videogame. 
You can handle:

 - Regions and Places of Teyvat
 - Artifacts
 - Domains
 - Main Goals and Goals
 - Materials
 - Weapons
 - Enemies
 - Characters ( plus Constellations and Talents)
 - a Blogpost
 - all users that can manage the application
 
 This project provides all basic CRUD operations and some other filters (like filter by name or by type) and uploading images.

## ⚙️ How to install

This project was developed using **Java Spring Boot**

1. Download this project from **GitHub** and clone it on your PC
2. Open it in your IDE (mine is IntelliJ) and create an `env.properties` file at the same level of `env.example` file
3. Copy the content of `env.example` file in `env.properties` file and fill in the fields with your data
   - To use this project, it's necessary have a Cloudinary account and a Database handler (I use PostegreSQL and PgAdmin4)
 4. Run the project
     - To view API documentation, import the `api_doc.yaml` file into Postman
    ❗DISCLAIMER: it's necessary create an admin account: you can create in the right table of your database or using the  /register endpoint in Postman❗
    
5. You can use this in a frontend app downloading the following project :
	https://github.com/NoemiP94/genshin-world
6. Follow the instructions in it to run the project

## 🖊️ Author

Noemi Pusceddu 🦋
🧑‍💻 [LinkedIn](https://www.linkedin.com/in/noemi-pusceddu-developer/)




  







<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>

    <div class="container">
        
        <h1>Join our email list</h1>
        <p>To join our email list, enter your name and email address below.</p>
        
        <p><i>${message}</i></p>

        <form action="emailList" method="post">
            <input type="hidden" name="action" value="add">        
            
            <label>Email:</label>
            <input type="email" name="email" value="${user.email}" required placeholder="Enter your email">
            
            <label>First Name:</label>
            <input type="text" name="firstName" value="${user.firstName}" required placeholder="John">
            
            <label>Last Name:</label>
            <input type="text" name="lastName" value="${user.lastName}" required placeholder="Doe">
            
            <input type="submit" value="Gui mail">
        </form>

    </div> 
    </body>
</html>
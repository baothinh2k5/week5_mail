<body>
    <div class="container">
        <h1>Thanks for joining!</h1>
        <p>Here is the information that you entered:</p>
        
        <label>Email:</label>
        <input type="text" value="${user.email}" readonly> <br> <form action="emailList" method="post">
            <input type="hidden" name="action" value="join">
            <input type="submit" value="Return">
        </form>
    </div>
</body>
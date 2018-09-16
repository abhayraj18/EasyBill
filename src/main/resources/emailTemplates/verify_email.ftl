<html>
    <body>
        Hi ${name}, <br/>
        <#if isNewUser>
        	Thank you for creating account with EasyBill.<br/>
        <#else>
        	Thanks for updating your email.<br/>
        </#if>
        Please verify your email by clicking below link.<br/>
        <a href = "${url}?id=${id}&token=${token}"> Verify Email </a>
        <br/><br/>
        
        Thanks,<br/>
        EasyBill
    </body>
</html>
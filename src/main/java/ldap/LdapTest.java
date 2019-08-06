package ldap;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class LdapTest {

    /*
        <ldap.principal.suffix>,ou=topscore,dc=tianchuang,dc=com,dc=cn</ldap.principal.suffix>
        <ldap.search.base>DC=tianchuang,DC=com,DC=cn</ldap.search.base>
        <ldap.url>ldap://192.168.2.13:389</ldap.url>
        <ldap.system.username>O2M</ldap.system.username>
        <ldap.system.password>tch*2016</ldap.system.password>
        <topscore.ad.domain>@tianchuang.com.cn</topscore.ad.domain>
        <topscore.ad.servertype>2</topscore.ad.servertype>
        <ldap.host>ldap://192.168.2.13:389</ldap.host>
        <ldap.treeName>OU=topscore,DC=tianchuang,DC=com,DC=cn</ldap.treeName>
     */

    public static void main(String[] args) throws NamingException, UnsupportedEncodingException {

        // ladp的一些配置
        Hashtable env = new Hashtable();
        String adminName = "O2M"; //管理员账号
        String adminPassword = "tch*2016";      //管理员密码
        String userName = ("CN=68481,OU=topscore,DC=tianchuang,DC=com,DC=cn"); //用户
        String newPassword = "123456";   //用户新密码
        String ldapURL = "ldaps://192.168.2.13:636";


        String keystore = "C:\\Program Files\\Java\\jdk1.8.0_201\\jre\\lib\\security\\cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification","true");



        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);

        try {
            // 初始化ldapcontext
            LdapContext ctx = new InitialLdapContext(env, null);
            ModificationItem[] mods = new ModificationItem[1];
            String newQuotedPassword = "\"" + newPassword + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            // 修改密码
            ctx.modifyAttributes(userName, mods);
            System.out.println("Reset Password for: " + userName);
            ctx.close();
        } catch (Exception e) {
            System.out.println("Problem resetting password: " + e);
            e.printStackTrace();

        }
    }
}

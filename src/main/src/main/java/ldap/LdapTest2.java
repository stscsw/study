package ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Hashtable;

public class LdapTest2 {

    private static final String RETURN_ATTS[] = {"pager"};
    private static final String SEARCH_FILTER_STR = "(&(objectCategory=person)(objectClass=user)(sAMAccountName={0}))";

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
        String adminName = "O2M和AD集成"; //管理员账号
        String adminPassword = "tch*2016";      //管理员密码
        String ldapSearchBase = ("DC=topscore,DC=com,DC=cn"); //用户
        String newPassword = "GGJY08481321!";   //用户新密码
        String ldapURL = "ldaps://192.168.2.1:636";


        String keystore = "D:\\cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", "true");


        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);

        try {
            String newQuotedPassword = "\"" + newPassword + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("unicodePwd", newUnicodePassword));
            LdapContext ctx = new InitialLdapContext(env, null);


            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(RETURN_ATTS);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> answer = ctx.search(ldapSearchBase, getMergeSearchFilter("68307"),
                    searchCtls);
            String nameInNamespace = answer.next().getNameInNamespace();
            ctx.modifyAttributes(nameInNamespace, mods);
            System.out.println("Reset Password for: " + nameInNamespace);
            ctx.close();
        } catch (Exception e) {
            System.out.println("Problem resetting password: " + e);
            e.printStackTrace();
        }
    }

    private static String getMergeSearchFilter(String loginName) {
        return MessageFormat.format(SEARCH_FILTER_STR, loginName);
    }
}

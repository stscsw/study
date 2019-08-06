package ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class LdapTest1 {

    private static final String RETURN_ATTS[] = {"pager", "name","unicodePwd"};

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
//        execute();
        editPwd();

    }

    public static String execute() {
        Hashtable env = new Hashtable();
        String LDAP_URL = "ldap://192.168.2.13:389"; // LDAP访问地址
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "O2M");
        env.put(Context.SECURITY_CREDENTIALS, "tch*2016");
        try {
            InitialDirContext dc = new InitialDirContext(env);// 初始化上下文
            // 域节点
            String searchBase = "DC=tianchuang,DC=com,DC=cn";
            String searchFilter = "cn=" + 68481;
            SearchControls searchCtls = new SearchControls(); // Create the
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Specify
            //设置查询的属性,根据登陆用户姓名获取ou
            searchCtls.setReturningAttributes(RETURN_ATTS);
            // 根据设置的域节点、过滤器类和搜索控制器搜索LDAP得到结果
            NamingEnumeration<SearchResult> entries = dc.search(searchBase, searchFilter, searchCtls);
            SearchResult entry = entries.next();
            Attributes attrs = entry.getAttributes();
            System.out.println("认证成功");//这里可以改成异常抛出。
            return "success";
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("认证失败");
            return "fail";
        } catch (Exception e) {
            System.out.println("认证出错：" + e);
            return "fail";
        }
    }

    public static void editPwd() throws NamingException {
        //ladp的一些配置
        Hashtable env = new Hashtable();
        String newPassword = "123456";
        String keystore = "C:\\Program Files\\Java\\jdk1.8.0_201\\jre\\lib\\security\\cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "O2M");
        env.put(Context.SECURITY_CREDENTIALS, "tch*2016");
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        String LDAP_URL = "ldaps://192.168.2.13:636";
        env.put(Context.PROVIDER_URL, LDAP_URL);
        try {
            String userName = "68481";
            //初始化ldapcontext
            LdapContext ctx = new InitialLdapContext(env, null);
            ModificationItem[] mods = new ModificationItem[1];
            String newQuotedPassword = "\"" + newPassword + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));
            // 修改密码
            ctx.modifyAttributes(userName, mods);
            System.out.println("Reset Password for: " + userName);
            ctx.close();
        } catch (NamingException | UnsupportedEncodingException e) {
            System.out.println("Problem resetting password: " + e);
            e.printStackTrace();
        }
    }
}

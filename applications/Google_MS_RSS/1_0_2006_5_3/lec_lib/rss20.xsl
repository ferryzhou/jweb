<?xml version="1.0" encoding="gb2312"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes" doctype-public="-//Netscape Communications//DTD RSS 0.91//EN" doctype-system="http://my.netscape.com/publish/formats/rss-0.91.dtd"/>

<xsl:template match="/">
<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/">
  <channel>
    <title>新浪新闻</title>
    <description>Powered by Ferry(v0.02)</description>
    <link>http://www.myjavaserver.com/~ferryzhou/rss/rss_main.htm</link>
    <language>zh-cn</language>
    <dc:creator>ferryzhou@gmail.com</dc:creator>
    <image>
        <title>My Blog：</title>
        <url>http://www.myjavaserver.com/~ferryzhou/chromatic.jpg</url>
        <link>http://ferryslife.blogbus.com/index.html</link>
    </image>
    
  <xsl:for-each select="//item">
  <item>
    <title><xsl:value-of select="title"/></title>
    <link><xsl:value-of select="url"/></link>
    <description><xsl:value-of select="content"/></description>
    <pubDate><xsl:value-of select="date"/></pubDate>
    <dc:creator><xsl:value-of select="author"/></dc:creator>
    <dc:subject><xsl:value-of select="class2"/></dc:subject>
  </item>
  </xsl:for-each>

  </channel>
</rss>
</xsl:template>

</xsl:stylesheet>
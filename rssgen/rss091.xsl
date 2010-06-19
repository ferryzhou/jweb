<?xml version="1.0" encoding="gb2312"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes" doctype-public="-//Netscape Communications//DTD RSS 0.91//EN" doctype-system="http://my.netscape.com/publish/formats/rss-0.91.dtd"/>

<xsl:template match="/">
<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/">
  <channel>
    <title>嫺�9710</title>
    <description />
    <link>http://bbs.ustc.edu.cn/</link>
    <language>zh_cn</language>
    <dc:creator>ferryzhou@hotmail.com</dc:creator>
    
  <xsl:for-each select="//item">
  <item>
    <title><xsl:value-of select="title"/></title>
    <link><xsl:value-of select="url"/></link>
    <description></description>
    <pubDate><xsl:value-of select="date"/></pubDate>
    <author><xsl:value-of select="author"/></author>
  </item>
  </xsl:for-each>

  </channel>
</rss>
</xsl:template>

</xsl:stylesheet>
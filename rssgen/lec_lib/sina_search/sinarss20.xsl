<?xml version="1.0" encoding="gb2312"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes" doctype-public="-//Netscape Communications//DTD RSS 0.91//EN" doctype-system="http://my.netscape.com/publish/formats/rss-0.91.dtd"/>

<xsl:template match="/">
<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/">
  <channel>
    <title>��������</title>
    <description>Power by Ferry</description>
    <link>http://news.sina.com.cn/</link>
    <language>zh-cn</language>
    <dc:creator>ferryzhou@hotmail.com</dc:creator>
    
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
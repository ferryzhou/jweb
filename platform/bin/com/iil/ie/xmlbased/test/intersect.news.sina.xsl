<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:date="http://exslt.org/dates-and-times"
xmlns:set="http://exslt.org/sets"
extension-element-prefixes="date set">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="/">
<xsl:for-each select="set:intersection(//text()[contains(.,'国内足坛')]/following::a, //text()[contains(.,'国际足坛')]/preceding::a)">
<a>
  <xsl:value-of select="."/>
</a>
</xsl:for-each>
</xsl:template>
<xsl:template match="text()"/>

</xsl:stylesheet>
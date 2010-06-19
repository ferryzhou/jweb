<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output encoding="gb2312" indent="yes"/>

<xsl:template match="//text()[contains(., '合肥')]">
<hefei>
  <tonight>
  <condition><xsl:value-of select="following::text()[1]"/></condition>
  <wind><xsl:value-of select="following::text()[2]"/></wind>
  <low><xsl:value-of select="following::text()[3]"/></low>
  </tonight>
  <tomorrow>
  <condition><xsl:value-of select="following::text()[4]"/></condition>
  <wind><xsl:value-of select="following::text()[5]"/></wind>
  <low><xsl:value-of select="following::text()[6]"/></low>
  </tomorrow>
</hefei>
</xsl:template>
<xsl:template match="text()"/>

</xsl:stylesheet>
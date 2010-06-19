<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:date="http://exslt.org/dates-and-times">
<xsl:output method="text"/>

<xsl:template match="/">
<xsl:value-of select="date:date()"/>
</xsl:template>

</xsl:stylesheet>
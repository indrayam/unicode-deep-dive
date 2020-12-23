# Unicode and Java

## Summary
- UTF-8 saves space. It can use anywhere from 1 byte to 4 bytes
- **Code points** are numbers that represent Unicode characters. One or more code units encode a single code point.
- **Code units** are numbers that encode code points, to store or transmit Unicode text. As stated above, one or more
  code units encode a single code point. Each code unit has the same size, which depends on the encoding format that is
  used. The most popular format, UTF-8, has 8-bit code units.
- The first version of Unicode had 16-bit code points. Translation: Each UTF encoded character used two Code Units. The
  use of 16 bits allowed direct representation of 65,536 unique characters, but this is not nearly enough to cover all
  the symbols used in human languages. Unicode version 4.1 includes over 97,000 characters, with over 70,000 characters
  for Chinese alone. The Unicode standard has established 16 additional "planes" of characters, each the same size as
  the BMP. Naturally, most code points beyond the BMP do not yet have characters assigned to them, but definition of the
  planes gives Unicode the potential to define 1,114,112 characters (that is, 2¹⁶ * 17 characters) within the code point
  range U+0000 to U+10FFFF.
- Bottom line, the size of code points was extended to 21 bits. These 21 bits are partitioned in 17 planes, with 16 bits
  each:
    + Plane 0: **Basic Multilingual Plane (BMP)**, 0x0000–0xFFFF Contains characters for almost all modern languages (
      Latin characters, Asian characters, etc.) and many symbols.
    + Plane 1: Supplementary Multilingual Plane (SMP), 0x10000–0x1FFFF Supports historic writing systems (e.g., Egyptian
      hieroglyphs and cuneiform) and additional modern writing systems. Supports emojis and many other symbols.
    + Plane 2: Supplementary Ideographic Plane (SIP), 0x20000–0x2FFFF Contains additional CJK (Chinese, Japanese,
      Korean) ideographs.
    + Plane 3–13: Unassigned
    + Plane 14: Supplementary Special-Purpose Plane (SSP), 0xE0000–0xEFFFF
        - Contains non-graphical characters such as tag characters and glyph variation selectors.
    + Plane 15–16: Supplementary Private Use Area (S PUA A/B), 0x0F0000–0x10FFFF
        - Available for character assignment by parties outside the ISO and the Unicode Consortium. Not standardized.
          Planes 1-16 are called supplementary planes or **astral planes.**
- Characters whose code points are greater than U+FFFF are called *Supplementary characters*. Those code points are
  called *Supplementary Code Points*. In other words, a *Supplementary Character* is a character located beyond the **
  BMP**, and a "surrogate" is a UTF-16 code value. For UTF-16, a "surrogate pair" is required to represent a single
  supplementary character. The first **(high) surrogate** is a 16-bit code value in the range U+D800 to U+DBFF. The
  second **(low) surrogate** is a 16-bit code value in the range U+DC00 to U+DFFF. Using the surrogate mechanism, UTF-16
  can support all 1,114,112 potential Unicode characters.
- The Java platform uses the UTF-16 representation in char arrays and in the String and StringBuffer classes:

```
0 - 65,535 (0x0000 => 0xFFFF)
0x10000 - 0x10FFFF (65,536 => 1,114,111)
Number of additional Unicode Characters: 1,048,575

High Surrogate: 55,296 => 56,319 (0xd800 => 0xdbff) 1023 (2**10)
Low Surrogate: 56,320 => 57,343 (0xdc00 => 0xdfff) 1023 (2**10)

Translation:
By using two 16-bit code values to define these additional Unicode characters, we get an additional ~1,048,576 (2**20) characters to represent the characters from non-MBP planes (or **astral** planes)
```

- A char value, therefore, represents Basic Multilingual Plane (BMP) code points, including the surrogate code points,
  or code units of the UTF-16 encoding.
- An int value represents all Unicode code points, including supplementary code points. The lower (least significant) 21
  bits of int are used to represent Unicode code points and the upper (most significant) 11 bits must be zero.

## Formal Documentation from JavaDoc documentation of Character class in JDK 11

The Character class wraps a value of the primitive type `char` in an object. An object of class Character contains a
single field whose type is char. In addition, this class provides a large number of static methods for determining a
character's category (lowercase letter, digit, etc.) and for converting characters from uppercase to lowercase and vice
versa.

### Unicode Conformance

The fields and methods of class Character are defined in terms of character information from the Unicode Standard,
specifically the UnicodeData file that is part of the Unicode Character Database. This file specifies properties
including name and category for every assigned Unicode code point or character range. The file is available from the
Unicode Consortium at http://www.unicode.org . The Java SE 11 Platform uses character information from version 10.0 of
the Unicode Standard, with an extension. The Java SE 11 Platform allows an implementation of class Character to use the
Japanese Era code point, U+32FF, from the first version of the Unicode Standard after 10.0 that assigns the code point.
Consequently, the behavior of fields and methods of class Character may vary across implementations of the Java SE 11
Platform when processing the aforementioned code point ( outside of version 10.0 ), except for the following methods
that define Java identifiers: isJavaIdentifierStart(int), isJavaIdentifierStart(char), isJavaIdentifierPart(int), and
isJavaIdentifierPart(char). Code points in Java identifiers must be drawn from version 10.0 of the Unicode Standard.

### Unicode Character Representations

The `char` data type (and therefore the value that a Character object encapsulates) are based on the original Unicode
specification, which defined characters as fixed-width 16-bit entities. The Unicode Standard has since been changed to
allow for characters whose representation requires more than 16 bits. The range of legal code points is now U+0000 to
U+10FFFF, known as Unicode scalar value. (Refer to the definition of the U+n notation in the Unicode Standard.)
The set of characters from U+0000 to U+FFFF is sometimes referred to as the **Basic Multilingual Plane (BMP)**.
Characters whose code points are greater than U+FFFF are called *supplementary characters*. The Java platform uses the
UTF-16 representation in char arrays and in the String and StringBuffer classes. In this representation, supplementary
characters are represented as a pair of char values, the first from the high-surrogates range, (\uD800-\uDBFF), the
second from the low-surrogates range (\uDC00-\uDFFF). A char value, therefore, represents Basic Multilingual Plane (BMP)
code points, including the surrogate code points, or code units of the UTF-16 encoding. An int value represents all
Unicode code points, including supplementary code points. The lower (least significant) 21 bits of int are used to
represent Unicode code points and the upper (most significant) 11 bits must be zero. Unless otherwise specified, the
behavior with respect to supplementary characters and surrogate char values is as follows:
The methods that only accept a char value cannot support supplementary characters. They treat char values from the
surrogate ranges as undefined characters. For example, Character.isLetter('\uD840') returns false, even though this
specific value if followed by any low-surrogate value in a string would represent a letter. The methods that accept an
int value support all Unicode characters, including supplementary characters. For example, Character.isLetter(0x2F81A)
returns true because the code point value represents a letter (a CJK ideograph). In the Java SE API documentation,
Unicode code point is used for character values in the range between U+0000 and U+10FFFF, and Unicode code unit is used
for 16-bit char values that are code units of the UTF-16 encoding. For more information on Unicode terminology, refer to
the Unicode Glossary

## References

- [Unicode – a brief introduction (advanced)](https://exploringjs.com/impatient-js/ch_unicode.html#:~:text=Code%20points%20are%20numbers%20that,encoding%20format%20that%20is%20used.)
- [What is UTF-8 Encoding? A Guide for Non-Programmers](https://blog.hubspot.com/website/what-is-utf-8)
- [How Unicode Works: What every developer needs to know about strings and 🦄](https://deliciousbrains.com/how-unicode-works/)
- [https://www.joelonsoftware.com/2003/10/08/the-absolute-minimum-every-software-developer-absolutely-positively-must-know-about-unicode-and-character-sets-no-excuses/](https://www.joelonsoftware.com/2003/10/08/the-absolute-minimum-every-software-developer-absolutely-positively-must-know-about-unicode-and-character-sets-no-excuses/)

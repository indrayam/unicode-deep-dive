# Unicode and Java

## Terminology

- A *character* is just an abstract minimal unit of text. It doesn't have a fixed shape (that would be a glyph), and it
  doesn't have a value. "A" is a character, and so is "€", the symbol for the common currency of Germany, France, and
  numerous other European countries.
- A *character set* is a collection of characters. For example, the Han characters are the characters originally
  invented by the Chinese, which have been used to write Chinese, Japanese, Korean, and Vietnamese.
- A *coded character set* is a character set where each character has been assigned a unique number. At the core of the
  Unicode standard is a coded character set that assigns the letter "A" the number 0x0041 and the letter "€" the number
  0x20AC. The Unicode standard always uses hexadecimal numbers, and writes them with the prefix "U+", so the number
  for "A" is written as "U+0041".
- *Code points* are the numbers that can be used in a coded character set. A coded character set defines a range of
  valid code points, but doesn't necessarily assign characters to all those code points. The valid code points for
  Unicode are U+0000 to U+10FFFF. Unicode 13.0 assigns characters to 143,859 of these more than a million code points.
- *Supplementary characters* are characters with code points in the range U+10000 to U+10FFFF, that is, those characters
  that could not be represented in the original 16-bit design of Unicode. The set of characters from U+0000 to U+FFFF is
  sometimes referred to as the Basic Multilingual Plane (BMP). Thus, each Unicode character is either in the BMP or a
  supplementary character.
- A *character encoding scheme* is a mapping from the numbers of one or more coded character sets to sequences of one or
  more fixed-width code units. The most commonly used code units are bytes, but 16-bit or 32-bit integers can also be
  used for internal processing. **UTF-32**, **UTF-16**, and **UTF-8** are character encoding schemes for the coded
  character set of the Unicode standard.
- *UTF-32* simply represents each Unicode code point as the 32-bit integer of the same value. It's clearly the most
  convenient representation for internal processing, but uses significantly more memory than necessary if used as a
  general string representation.
- *UTF-16* uses sequences of one or two unsigned 16-bit code units to encode Unicode code points. Values U+0000 to
  U+FFFF are encoded in one 16-bit unit with the same value. Supplementary characters are encoded in two code units, the
  first from the high-surrogates range (U+D800 to U+DBFF), the second from the low-surrogates range (U+DC00 to U+DFFF).
  This may seem similar in concept to multi-byte encodings, but there is an important difference: The values U+D800 to
  U+DFFF are reserved for use in UTF-16; no characters are assigned to them as code points. This means, software can
  tell for each individual code unit in a string whether it represents a one-unit character or whether it is the first
  or second unit of a two-unit character. This is a significant improvement over some traditional multi-byte character
  encodings, where the byte value 0x41 could mean the letter "A" or be the second byte of a two-byte character.
- *UTF-8* uses sequences of one to four bytes to encode Unicode code points. U+0000 to U+007F are encoded in one byte,
  U+0080 to U+07FF in two bytes, U+0800 to U+FFFF in three bytes, and U+10000 to U+10FFFF in four bytes. UTF-8 is
  designed so that the byte values 0x00 to 0x7F always represent code points U+0000 to U+007F (the Basic Latin block,
  which corresponds to the ASCII character set). These byte values never occur in the representation of other code
  points, a characteristic that makes UTF-8 convenient to use in software that assigns special meanings to certain ASCII
  characters.

## Encoding UTF-16

Encoding of a single character from an ISO 10646 character value to UTF-16 proceeds as follows. Let U be the character
number, no greater than 0x10FFFF.

- If U < 0x10000, encode U as a 16-bit unsigned integer and terminate.
- Let U' = U - 0x10000. Because U is less than or equal to 0x10FFFF, U' must be less than or equal to 0xFFFFF. That is,
  U' can be represented in 20 bits.
- Initialize two 16-bit unsigned integers, W1 and W2, to 0xD800 and 0xDC00, respectively. These integers each have 10
  bits free to encode the character value, for a total of 20 bits.
- Assign the 10 high-order bits of the 20-bit U' to the 10 low-order bits of W1 and the 10 low-order bits of U' to the
  10 low-order bits of W2. Terminate.

Graphically, steps 2 through 4 look like:

```
U' = yyyyyyyyyyxxxxxxxxxx
W1 = 110110yyyyyyyyyy
W2 = 110111xxxxxxxxxx
```

## Decoding UTF-16

Decoding of a single character from UTF-16 to an ISO 10646 character value proceeds as follows. Let W1 be the next
16-bit integer in the sequence of integers representing the text. Let W2 be the (eventual)
next integer following W1.

- If W1 < 0xD800 or W1 > 0xDFFF, the character value U is the value of W1. Terminate.
- Determine if W1 is between 0xD800 and 0xDBFF. If not, the sequence is in error and no valid character can be obtained
  using W1. Terminate.
- If there is no W2 (that is, the sequence ends with W1), or if W2 is not between 0xDC00 and 0xDFFF, the sequence is in
  error. Terminate.
- Construct a 20-bit unsigned integer U', taking the 10 low-order bits of W1 as its 10 high-order bits and the 10
  low-order bits of W2 as its 10 low-order bits.

## Overall Summary

- The UTF-16 encoding represents all Unicode code points in a variable-length code thereby saving space.
- **Code points** are numbers that represent Unicode characters. One or more code units encode a single code point.
- **Code units** are numbers that encode code points, to store or transmit Unicode text. As stated above, one or more
  code units encode a single code point. Each code unit has the same size, which depends on the encoding format that is
  used. For example, UTF-16, has 16-bit code units.
- The first version of Unicode had 16-bit code points. Translation: Each UTF encoded character used one code unit. The
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
  called *Supplementary Code Points*. The supplementary characters are encoded as consecutive pairs of code units. Each
  of the values in such an encoding pair falls into a range of 2048 unused values of the basic multilingual plane,
  called the **surrogates area** (see below). *This is rather clever, because you can immediately tell whether a code
  unit encodes a single character or it is the first or second part of a supplementary character.*
- For UTF-16, a "surrogate pair" is required to represent a single supplementary character. The first **(high)
  surrogate** is a 16-bit code value in the range U+D800 to U+DBFF. The second **(low) surrogate** is a 16-bit code
  value in the range U+DC00 to U+DFFF. Using the surrogate mechanism, UTF-16 can support all 1,114,112 potential Unicode
  characters.
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

- [Supplementary Characters in the Java Platform](https://www.oracle.com/technical-resources/articles/javase/supplementary.html)
- [Unicode – a brief introduction (advanced)](https://exploringjs.com/impatient-js/ch_unicode.html#:~:text=Code%20points%20are%20numbers%20that,encoding%20format%20that%20is%20used.)
- [What is UTF-8 Encoding? A Guide for Non-Programmers](https://blog.hubspot.com/website/what-is-utf-8)
- [How Unicode Works: What every developer needs to know about strings and 🦄](https://deliciousbrains.com/how-unicode-works/)
- [https://www.joelonsoftware.com/2003/10/08/the-absolute-minimum-every-software-developer-absolutely-positively-must-know-about-unicode-and-character-sets-no-excuses/](https://www.joelonsoftware.com/2003/10/08/the-absolute-minimum-every-software-developer-absolutely-positively-must-know-about-unicode-and-character-sets-no-excuses/)
- [RFC: Encoding UTF-16](https://tools.ietf.org/html/rfc2781#section-2.1)

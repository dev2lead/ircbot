##
# Nicolas THIBAUT
# http://dev2lead.com
##

NAME=		IRCbot

SRCDIR=
OBJDIR=

SRC=		$(SRCDIR)Main.java
OBJ=		$(SRC:$(SRCDIR)%.java=$(OBJDIR)%.class)

all:
		javac $(SRC)
		jar -cvmf Manifest.mf $(NAME).jar $(OBJ)
		proguard -injars $(NAME).jar -outjars $(NAME).pro.jar @Config.pro

clean:
		rm -f $(OBJ)

fclean:		clean
		rm -f $(NAME).jar
		rm -f $(NAME).pro.jar

re:		fclean all

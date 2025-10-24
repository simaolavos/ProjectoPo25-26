all:
	(cd po-uilib; make $(MFLAGS) all)
	(cd bci-core; make $(MFLAGS) all)
	(cd bci-app; make $(MFLAGS) all)

clean:
	(cd po-uilib; make $(MFLAGS) clean)
	(cd bci-core; make $(MFLAGS) clean)
	(cd bci-app; make $(MFLAGS) clean)

install:
	(cd po-uilib; make $(MFLAGS) install)
	(cd bci-core; make $(MFLAGS) install)
	(cd bci-app; make $(MFLAGS) install)

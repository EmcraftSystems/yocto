def subst_git_rev(rel, sha):
        import re
        sha = sha.replace("AUTOINC+", "")
        m = re.search("G{4,40}", rel)
        if m:
                return rel.replace(m.group(0), sha[:len(m.group(0))])

def get_yocto_rev(d):
        for l in (d.getVar("BBLAYERS", True) or "").split():
                if l.find('meta-emcraft') != -1:
                        f = os.popen("cd %s; git status -uno --porcelain 2>&1" % l)
                        data = f.read()
                        f.close()
                        if data:
                                data = 'M'
                        else:
                                data = ''
                        lrev = base_get_metadata_git_revision(l, None)
                        if lrev != "<unknown>":
                                return data + base_get_metadata_git_revision(l, None)
        return '0000'

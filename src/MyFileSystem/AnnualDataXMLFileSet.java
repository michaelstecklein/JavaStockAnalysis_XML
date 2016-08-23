package MyFileSystem;

import java.util.Set;

public class AnnualDataXMLFileSet extends UpdatableFileSet {

	private Set<FileType> fileSet;
	
	@Override
	public Set<FileType> getFileSet() {
		return fileSet;
	}
	
	@Override
	public boolean create() {
		addMissingAnnualFiles();
		return false;
	}
	
	@Override
	public boolean update() {
		addMissingAnnualFiles();
		return super.update();
	}
	
	private void addMissingAnnualFiles() {
		// TODO (finish AnnualDataXMLFile first)
	}

}

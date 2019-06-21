package jp.co.sss.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.sss.attendance.entity.Registry;
import jp.co.sss.attendance.repository.RegistryRepository;

@Service
public class RegistryService {
//	public Page<Registry> findPaginated(Pageable pageable) {		
//		@Autowired
//	    private RegistryRepository repo;
//		@Override
//	    public Page<Registry> getPaginatedRegistries(Pageable pageable) {
//	        return repo.findAll(pageable);
//	    }
//	}
//	}
//		int pageSize = pageable.getPageSize();
//		int currentPage = pageable.getPageNumber();
//		int startItem = currentPage * pageSize;
//		List<Book> list;
////		        if (books.size() < startItem) {
////		            list = Collections.emptyList();
////		        } else {
////		            int toIndex = Math.min(startItem + pageSize, books.size());
////		            list = books.subList(startItem, toIndex);
////		        }
//		Page<Registry> registryPage
//			= new PageImpl<Registry>(list, PageRequest.of(currentPage, pageSize), books.size());
//		
//		return registryPage;
//		    }
}

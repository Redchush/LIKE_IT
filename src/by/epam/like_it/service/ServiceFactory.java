package by.epam.like_it.service;


import by.epam.like_it.service.impl.EntityServiceImpl;
import by.epam.like_it.service.impl.system.PrevQueryServiceImpl;
import by.epam.like_it.service.impl.client.ClientServiceImpl;
import by.epam.like_it.service.impl.common.*;
import by.epam.like_it.service.impl.system.MessageServiceImpl;
import by.epam.like_it.service.impl.system.CashServiceImpl;

public class ServiceFactory {

	private static ServiceFactory instance;

	private ServiceFactory(){}

	public static ServiceFactory getInstance(){

		if (instance == null)
			synchronized (ServiceFactory.class){
				if (instance == null)
					instance = new ServiceFactory();
			}
		return instance;
	}
	/*entity services*/

	public UserService getUserService(){
		return UserServiceImpl.getInstance();
	}

	public PostService getPostService(){
		return PostServiceImpl.getInstance();
	}

	public TagService getTagService(){
		return TagServiceImpl.getInstance();
	}

	public AnswerService getAnswerService(){
		return AnswerServiceImpl.getInstance();
	}

	public ClientService getClientService(){return ClientServiceImpl.getInstance();}

	public EntityService getEntityService() {return EntityServiceImpl.getInstance(); }

	@Deprecated
	public CategoryService getCategoryService(){
		return CategoryServiceImpl.getInstance();
	}

	/*system services*/

	public MessageService getMessageService() {
		return MessageServiceImpl.getInstance();
	}

	public CashService getCashService() {return CashServiceImpl.getInstance();}

	public PrevQueryService getPrevQueryService(){return PrevQueryServiceImpl.getInstance();}


}

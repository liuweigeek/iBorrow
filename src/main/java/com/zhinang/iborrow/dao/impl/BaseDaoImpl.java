package com.zhinang.iborrow.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;


@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDao<T> {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
    public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	@Override
    public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	@Override
    public void update(T o) {
		this.getCurrentSession().update(o);
	}

	@Override
    public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	@Override
    public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@Override
    public List<T> find(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(Integer.toString(i), param[i]);
			}
		}
		return q.list();
	}

	@Override
    public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));

		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(Integer.toString(i), param.get(i));
			}
		}
		return q.list();
	}

	@Override
    public List<T> find(String hql, Object[] param, PageBean pageBean) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
			    q.setParameter(Integer.toString(i), param[i]);
			}
		}
		return q.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getPageSize()).list();
	}

	@Override
    public List<T> find(String hql, List<Object> param, PageBean pageBean) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(Integer.toString(i), param.get(i));
			}
		}
		return q.setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getPageSize()).list();
	}

	@Override
    public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
    public T get(String hql, Object[] param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
    public T get(String hql, List<Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
    public Long count(String hql) {
		return  (Long) this.getCurrentSession().createQuery(convertToJpa(hql)).uniqueResult();
	}

	@Override
    public Long count(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(Integer.toString(i), param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
    public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(Integer.toString(i), param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
    public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(convertToJpa(hql)).executeUpdate();
	}

	@Override
    public Integer executeHql(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(Integer.toString(i), param[i]);
			}
		}
		return q.executeUpdate();
	}

	@Override
    public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(Integer.toString(i), param.get(i));
			}
		}
		return q.executeUpdate();
	}

	@Override
    public void merge(T o) {
		this.getCurrentSession().merge(o);
	}

	@Override
    public Integer executeSql(String sql) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public List<T> findTopN(String hql, List<Object> param, int N) {
		Query q = this.getCurrentSession().createQuery(convertToJpa(hql));
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(Integer.toString(i), param.get(i));
			}
		}
		q.setFirstResult(0);
		q.setMaxResults(N);
		return q.list();
	}

	private static String convertToJpa(String hql) {
		String[] strArr = hql.split("[?]");
		//字符串中间存在占位符
		if (strArr.length > 1) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < strArr.length; i++) {
				//如果是最后一段字符串
				if (i == strArr.length -1) {
					//字符串尾部存在占位符
					if (hql.endsWith("?")) {
						sb.append(strArr[i]).append("?").append(i);
					} else {
						sb.append(strArr[i]);
					}
				} else {
					sb.append(strArr[i]).append("?").append(i);
				}
			}
			return sb.toString();
		} else {
			//字符串结尾有唯一一个占位符
			if (hql.endsWith("?")) {
				return hql + "0";
			} else {
				return hql;
			}
		}
	}

}

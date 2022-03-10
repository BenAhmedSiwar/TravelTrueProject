<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\HttpFoundation\Request;
use App\Form\UserType;
use App\Entity\User;
use App\Repository\UserRepository;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class UserController extends AbstractController


{
    private $repo ;
    
    public function __construct(Security $security , UserRepository $repo)
    {

       $this->repo= $repo;
       $this->security = $security;

    }
    

    /**
     * @Route("/user", name="user")
     */
    public function index(): Response
    {
        $user = $this->security->getUser() ;
        return $this->render('user/index.html.twig', [
            'controller_name' => 'UserController',
            'user' => $user 
        ]);


    }

    /**
     * @Route("/users", name="utilisateurs")
     */
    public function Utilisateurs(): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->findBy(['role' => 'user']);
        
        return $this->render('user/users.html.twig', [
            'controller_name' => 'UserController',
            'user' => $user 
        ]);


    }


     /**
     * @Route("/admins", name="admins")
     */
    public function admins(): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->findBy(['role' => 'admin']);
        
        return $this->render('user/admins.html.twig', [
            'controller_name' => 'UserController',
            'user' => $user 
        ]);


    }


     

    
    /**
     * @Route("/user/edit", name="userEdit")
     */

    public function Edit( Request $request): Response
    {
        $user = $this->security->getUser() ;
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            
            
            $file = $user->getPhoto();
            if($file){
                $uploads_directory = $this->getParameter('upload_directory');
                $fileName = md5(uniqid()).'.'.$file->guessExtension(); 
                $file->move(
                    $uploads_directory,
                    $fileName
                );
                $user->setPhoto($fileName);
            }
            
         else
            {
                $user->setPhoto($user->getPhoto());
            }

            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("user");
        }
        return $this->render('user/Details.html.twig', [
            'controller_name' => 'UserController',
            'user' => $user ,
            'form' => $form->createView(),
        ]);


    }

    


     /**
     * @Route("editUsers/{id}",name="usersEdit")
     */
    function Update(UserRepository $repo, $id, Request $request)
    {
        $user = $repo->find($id);
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $user->getPhoto();
            if($file){
                $uploads_directory = $this->getParameter('upload_directory');
                $fileName = md5(uniqid()).'.'.$file->guessExtension(); 
                $file->move(
                    $uploads_directory,
                    $fileName
                );
                $user->setPhoto($fileName);
            }
            
         else
            {
                $user->setPhoto($user->getPhoto());
            }
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('utilisateurs');
        }
        return $this->render('user/editUsersback.html.twig', [
            'form' => $form->createView()]);

    }

    /**
     * @Route("editAdmins/{id}",name="adminsEdit")
     */
    function UpdateA(UserRepository $repo, $id, Request $request)
    {
        $user = $repo->find($id);
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $user->getPhoto();
            if($file){
                $uploads_directory = $this->getParameter('upload_directory');
                $fileName = md5(uniqid()).'.'.$file->guessExtension(); 
                $file->move(
                    $uploads_directory,
                    $fileName
                );
                $user->setPhoto($fileName);
            }
            
         else
            {
                $user->setPhoto($user->getPhoto());
            }
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('admins');
        }
        return $this->render('user/editAdminsback.html.twig', [
            'form' => $form->createView()]);

    }





      /**
     * @Route("deleteUser/{id}",name="deleteUser")
     */
    function deleteS($id, UserRepository $repo)
    {
        $user = $repo->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        $this->addFlash("warning", "Reclamation supprimée avec succées");
        return $this->redirectToRoute('utilisateurs');
    }

    
      /**
     * @Route("deleteAdmin/{id}",name="deleteAdmin")
     */
    function deleteA($id, UserRepository $repo)
    {
        $user = $repo->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        $this->addFlash("warning", "Reclamation supprimée avec succées");
        return $this->redirectToRoute('admins');
    }


    
    /**
     * @Route("/Statistique", name="UsersByMonth")
     */
    public function Stat(): Response
    {
        
       $user = $this->repo->UsersByMonth();
       
       return $this->render('user/statistics.html.twig' , [
        'user' => $user ]);

    }

    


}

<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\HttpFoundation\Request;
use App\Form\UserType;
use App\Entity\User;

class UserController extends AbstractController


{

    public function __construct(Security $security)
    {
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
     * @Route("/users/edit", name="usersEdit")
     */
    public function EditUsers ( Request $request): Response
    {
        $user = $this->security->getUser() ;
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("utilisateurs");
        }
        return $this->render('user/editUsersback.html.twig', [
            'controller_name' => 'UserController',
            'user' => $user ,
            'form' => $form->createView(),
        ]);


    }



    


    


}
